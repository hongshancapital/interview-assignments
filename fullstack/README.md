# A short URL service with Express.js + TS + Mysql

## Arch
<img width="754" alt="image" src="https://user-images.githubusercontent.com/10559010/155990246-090aa958-4022-426c-b8c9-f57197d4ac85.png">

## Theory

The robust solution for a short url is to use a unique ID generator to generate an id for each long url, then choose some Bijective Function to convert this id to some short string and vice visa.

Redis is used for reading performance. Also notice that, it's normal if the user submit the same long url multiple times and generates different short urls each time, it's only wasting some storage, but to optimize this to a certain degree, I used an LRU cache, so that in a short time, the frequently submitted long urls will not be computed again to generate a new short url, instead, the already existed latest one will get returned.

## Assumptions

- Only support at most 8 char short url, notice that I am using base62 ([a-zA-Z0-9]), that means it can at most support 8^62 urls, which is enough for most cases.
- This is supposed to be deployed within a containeration system (such as k8s), we can afford to have multiple instances for robustness, thus no need to make the application itself into a cluster mode(nodejs cluster).
- To avoid one to many mapping (sort of storage wasting) of a long url, the system use an LRU cache to ease this pain, namely if a same long url is frequently been submitted to generate a short one, the system will return the already generated cache instead of generating a new short url again.
- Assume the longest url will be 2048, this should cover most of the cases.
- Don't support customized short url (user given short url), if need this, one easy way is to add one `short_url` column to the table and also stores the shortUrls instead of computing from ids, this way, we can index this column and also check against existence to support customized shortUrls.

## Test
<img width="893" alt="image" src="https://user-images.githubusercontent.com/10559010/155990777-fb233ddb-6629-44a3-9a92-52543fb0cd57.png">
<img width="893" alt="image" src="https://user-images.githubusercontent.com/10559010/155990841-769f7db9-e597-44fd-8158-65ece0f0f6b4.png">


## Code Structure
- `scripts` : the sql file used to create tables
- `src` : where express code lies
  - `controllers`: api routers
  - `libs`: util libs & db connectors
  - `middlewares`
  - `services`
  - `app.ts`: express application instance
  - `index.ts` : main entry
- `tests` : test cases


## ENV
- node: v14.17.3
- npm: 8.5.1