# TypeScript Backend Engineer Assignment

### Startup
#### Run using docker
```
docker-compose up -d --build
```
#### Develop at localhost
```
docker run -it --rm -p 27017:27017 mongo
npm build
npm start
```
#### Run Unit Tests
```
npm test
```

### Test Report
```
 PASS  tests/utils.spec.ts
 PASS  tests/controller.spec.ts
---------------|---------|----------|---------|---------|-------------------
File           | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
---------------|---------|----------|---------|---------|-------------------
All files      |     100 |      100 |     100 |     100 |                   
 controller.ts |     100 |      100 |     100 |     100 |                   
 model.ts      |     100 |      100 |     100 |     100 |                   
 utils.ts      |     100 |      100 |     100 |     100 |                   
---------------|---------|----------|---------|---------|-------------------

Test Suites: 2 passed, 2 total
Tests:       19 passed, 19 total
Snapshots:   0 total
Time:        1.659 s, estimated 3 s
```

### API Tests: Use cases and reports
1. Create a shorted domain for the original domain (or simply return the formerly shortened domain if it already exists)
```
 curl -L -X POST 'http://localhost:3000/api/shorten' -H 'Content-Type: application/json' -d '{"url": "https://www.original.com/new_url?a=1&n=2#123"}'              
{"url":"https://www.original.com/new_url?a=1&n=2#123","shortUrl":"https://www.original.com/00000000"}
```

2. Return the original domain for the shorten domain
```
curl -L 'http://localhost:3000/api/shorten/00000000' -H 'Content-Type: application/json'
{"url":"https://www.original.com/new_url?a=1&n=2#123","shortUrl":"https://www.original.com/00000000"} 
```

3. Return an error message when the input domain is invalid 
```
 curl -L -X POST 'http://localhost:3000/api/shorten' -H 'Content-Type: application/json' -d '{"url": "https:///new_url?a=1&n=2#123"}' 
{"message":"url is invalid"}
```

4. Return an error message when the input domain doesn't exist
```
curl -L 'http://localhost:3000/api/shorten/abcdef00' -H 'Content-Type: application/json'
{"message":"shortId is invalid"}
```

5. Return an error message with empty domain
```
curl -L -X POST 'http://localhost:3000/api/shorten' -H 'Content-Type: application/json' -d '{}'             
{"message":"url is not provided"}
```

### design principles
* rest api
* simple mvc
* document database (mongodb)
* containerized microservice

### project files
```
.
|-- main.ts // express server, entry point and routes
|-- app/controller.ts // api function definition
|-- app/model.ts // app model 
|-- app/utils.ts // utils function providing the main functionality

```
### REST API 
1. create a shorten endpoint:

Request
```
POST /api/shorten
```
Request Body
```
{
  "url": "string, required",
}
```

Response
```
{
  "url": "string",
  "shortUrl": "string"
}
```
2. fetch shortened domain endpoint:

Request
```
GET /api/shorten/:shortId
```

Response
```
{
  "url": "string",
  "shortUrl": "string"
}
```


### table schema
```
Document {
    url: string; // index, required, unique
    shortId: string; // index, required, unique
    shortened: string; // index, required, unique
    ...
}
```
