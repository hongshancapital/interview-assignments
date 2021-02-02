# Implementation of Short URL APIs

## General Analysis

At a glance the major job is to establish and keep associations between the original urs and its corresponding short url(s). There are two main typical operations needed:

1. creation: given an original url, to create a short url corresponding to it;  
2. query: given a short url, to located its corresponding original url;

Apparently, creation is more essential here. A short url should be associated with only one original url (or with nothing). An original url, however, seems possible to be associated with more than one short url and the system can still function. It is a decision to make. Another thing to consider is if it is need to support expiration of the url association.  

Take a look at the short url. Except the leading server name, short url is a fixed length token, and converted to a fix length number. For example, given a length of token of eight bytes, and with base64 encoding, each url can be represented by a unique six-byte (48-bit) number. This is a really big range, say, suppose 1000 different items being created per second, it will take 8900+ years to create all of them. ( 64^8 / (1000\*60\*60\*24\*365) is close to 8900). 

So, a short url can be identified with a number (referred as **short url id** or **id** hereafter). Hence, the task to associate an original url to a short url equals to bind the original url to a number of a specific range. A straight way is applying a hash function on the original url. The reverse way is to fetch a unique number of the range and assign it to the original url. Either has pros and cons. This is another thing to choose and refine. 

## Design Notes
### Assumptions	

The system can be regarded as a short url resource manager. So beyond to meet the functional and performance needs, an assumption is that short url resource should be utilized as efficiently as possible. Therefore, it should ensure:

- at extreme, all short urls (e.g. 2^48 if length set to 8 byte) should be created, and
- one original url should only be mapped to one short url, (1:1 mapping)
- the approach to issue short url ids to original urls is more suitable, as it can easily traverse the whole id range. (It is hard to devise a hash function  for this purpose. Besides, most hash functions are not free from collisions to make 48-bit result, yet handling collisions will bring much complexity.) 

What's more considering the potential data volume and request traffic, another assumption/goal is the system should be able to scale out (horizontally scale). 


### Short URL ID Generation

To traverse a range, a simple way is using an iterator with an atomic operation of check and set. The pain point of this solution is that the single iterator will become the bottleneck of concurrency, plus the single point of failure, heavily impacting scalability/availability. Below mechanism is proposed to deal with these issues:  

1. split the id into two parts, the first part decides a sub-range of the whole id range, and the second part represents a number out of that sub-range;

 e.g, a six-byte id can be split into two three-byte, or one two-byte and one four-byte respectively; 

2. given an original url (not be mapped before), select a range firstly, then fetch the number by the range's iterator. The id is then made by combining the range and the number. After that the id can be converted to a base64 short url with simple math. 

3. In case id is not created successfully in a range, just select another range and move on (unless all sub-ranges are exhausted)

With this approach, multiple ids can be made simultaneously in different ranges. This helps on concurrency/availability. Every sub-range can be fully iterated thus the whole range can be traversed. And it is easy to scale out, e.g., with doing consistent hash on range. 

### Use of Hash Function

Plain urls are not with fixed length, and in reality the longest urls may be with 2000+ characters. Building indices on variable length string is costly, and the querying performance is not good. With hash function, a string of variable length can be converted to a fixed-length value and serve as its digital fingerprint. 

MD5 is a popular for this purpose. Yet theoretically 128-bit MD5 is no longer free of conflicts. So an alternative RIPEMD160 algorithm is chosen, which makes a unique 160-bit result as a 40-byte hex string. This result is then used to  represent the original url in indexing and querying in the system.


##System Structure/Organization
### components
- API core logic implementation
- Validator to take care of request validity of request
- Data Storage to take care of <K, V> persistency and retrieval
- ID Generator to provide unique id for each plain url (old url)
- Cache to improve querying performance 
	 
	see below diagram for more information:

![System Components and Communication](./box-diagram.png)

### endpoints

- create:  POST, with request.body={original\_url:#original_url#}
- resolve:  GET, with request.pramas.short\_url=#short_url#

### scalability

Here api logic and id generator are most possible bottlenecks. 

- the api logic is stateless, this part can be easily scale out; 
- the id generator can be extracted and deployed as an independent service with a few changes. As discussed above, the algorithm itself supports scaling-out. 


## Data Model and Storage/Cache

### Data Model
as discussed above, there are two main kinds data models:

1. on id generator, the core is to save the range and its iterator. It can be implemented use a rational DB or key-value storm which supports atomic get and set operation (like redis). In the implementation mysql is used, and a sample table is like (range: 2 bytes, iterator: 4 bytes):

 Field    | Type              | Null | Key | Default | Extra 
 ---------|-------------------|------|-----|---------|-------
 range_id | smallint unsigned | NO   | PRI | NULL    |       
 sequence | int unsigned      | NO   | MUL | NULL    |     
   
2. to save the bindings of short url id and original url, the data volume and the simple query cases (no complicated joins/filtering) leads to key-value NoSQL storage. DynamoDB is used in the implementation, and the scheme is as follows:

 Attribute | Type | Property
 ----------|------|---------
 short_url_id|Number|hash key
 original_url|String|
 original_url_check_sum|String|Global Secondary Index 
 
### Cache Model: 
there two kinds of queries:
 
- one is in to resolve a short url, needs to save \<K, V\> of (short\_url\_id, original\_url); 

- the other is to check if an original url has been bound to a shortened one, need s to save \<K,V\> of (original\_url\_check\_sum, short\_url\_id). 
 
a simple lru-cache on app side is used so far. Further cache support, e.g., an independent redis service, or cache on NoSQL, can be used as needed. 

## Test

#### Unit Test: Jest

#### Integrated Test: Jest/Supertest
- different paths/scenarios to validate: 
	- Create: 
		1. valid input (original_url) and not created before => 201/CREATED, with json result
		2. valid input and created before => 200/OK, with json result
		3. valid input but not creation failed => 500/Internal Server Error 
		4. invalid input
		   - no original url provided  => 400/Bad Request
		   - original url is not a valid url => 400/Bad Request
		   - original url is already a short url in our system => 400/Bad Request
	- Resolve: 
	   1. valid input and created before => 200/OK, with json result
	   2. valid input but not created yet => 404/Not Found
	   3. invalid short_url => 404/Not Found
	      - not provided
	      - not a valid short url

#### UT/IT Results

File                            | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s   
--------------------------------|---------|----------|---------|---------|---------------------
All files                       |   94.85 |    84.62 |   96.36 |   95.38 |                     
src                             |      92 |    47.62 |     100 |      92 |                      
` `config.ts                    |   84.62 |    47.62 |     100 |   84.62 | 17-20,37            
` `constants.ts                 |     100 |      100 |     100 |     100 |                     
` `interfaces.ts                |     100 |      100 |     100 |     100 |                     
` `logger.ts                    |     100 |      100 |     100 |     100 |                     
` `src/cache                    |     100 |      100 |     100 |     100 |                     
` `` `app_cache.ts              |     100 |      100 |     100 |     100 |                     
` `src/controller               |   98.29 |    93.48 |     100 |     100 |                     
` `` `controller.ts             |   98.11 |    90.91 |     100 |     100 | 47,87               
` `` `utils.ts                  |   97.92 |    94.12 |     100 |     100 | 7                   
` `` `validator.ts              |     100 |      100 |     100 |     100 |                     
` `src/data_storage             |    87.8 |      100 |   85.71 |   87.18 |                     
` `` `data_storage.ts           |    87.8 |      100 |   85.71 |   87.18 | 83-84,94-101        
` `src/id_service               |    93.1 |    94.29 |   94.74 |    93.2 |                     
` `` `db_provider_mysql.ts      |   87.04 |    83.33 |     100 |   87.76 | 54-55,81-82,102-103 
` `` `id_service_impl.ts        |     100 |      100 |     100 |     100 |                     
` `` `id_service_intf.ts        |   90.91 |      100 |      80 |   90.91 | 24                  
` `` `id_service_repository_impl.ts |     100 |      100 |     100 |     100 |                     
` `src/integration_tests        |      95 |    77.78 |     100 |   97.14 |                     
` `` `id_service_simple.ts      |      92 |    77.78 |     100 |      95 | 23                  
` `` `inversify.4test.config.ts |     100 |      100 |     100 |     100 |                     
` `src/server                   |     100 |      100 |     100 |     100 |                     
` `` `app.ts                    |     100 |      100 |     100 |     100 |                     


_Test Suites: 8 passed, 8 total_<br>
_Tests:       45 passed, 45 total_<br>
_Snapshots:   0 total_<br>
_Time:        2.608 s, estimated 12 s_<br>
_Ran all test suites._<br>

#### End to End Test (with curl/postman and so on)
Can proceed like below examples (suppose the server is running on localhost at port 6000 with access point "/api/v1/short\_url":

➜  #✗ _curl -H "Content-Type: application/json" -X POST -d '{"original\_url":"http://www.vim.org"}' localhost:6000/api/v1/short\_url_
{"short\_url":"https://d.io/44=444t4","original_url":"http://www.vim.org"}                                                                                   
➜  #✗ _curl localhost:6000/api/v1/short\_url/https%3A%2F%2Fd.io%2F44=444t4_
{"short\_url":"https://d.io/44=444t4","original\_url":"http://www.vim.org"}     


##To Do
So far the implementation is a simplified proof of concept for the design discussed above. A lot of work can be done further as follows:   

1. support delete operation (to clear a mapping in the system)  
2. support batch operations (create or resolve multiple urls)
3. timeout and retry mechanism 
4. support expiration: ttl is to be added
5. security check and access control (authentication/authorization, throttling/rate limiter,  etc.)
6. and so on ...
