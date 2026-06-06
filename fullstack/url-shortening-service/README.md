# URL Shortening Service built with TypeScript + Express.js + MongoDB

## APIs
| Path        | Method | Description                  | Parameters       |   |
|-------------|--------|------------------------------|------------------|---|
| /           | GET    | Redirect to the original URL | /:urlCode        |   |
| /shortenUrl | POST   | Create short URL             | { originalUrl }  |   |
| /getUrl     | GET    | Get short URL info           | /getUrl/:urlCode |   |

## Core Url Schema
| Field       | Type   | PK | Description                       |   |
|-------------|--------|----|-----------------------------------|---|
| urlCode     | String | Y  | short code for the original url   |   |
| originalUrl | String | N  | original url                      |   |
| shortUrl    | String | N  | short url with the generated code |   |
| createDate  | String | N  | short url created date            |   |

## TODOs
* Url remove API for user to manually request url removal
* Auto cleanup for the url data after a certain time range based on the `createDate` field
* Cache layer to fasten the read speed if needed
* Data partitioning and replication if needed
* User authentication and TPS limit if needed

## Unit Test Reports
```
-----------------|---------|----------|---------|---------|-------------------
File             | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
-----------------|---------|----------|---------|---------|-------------------
All files        |   95.45 |     87.5 |     100 |   95.04 |                   
 src             |     100 |      100 |     100 |     100 |                   
  app.ts         |     100 |      100 |     100 |     100 |                   
 src/config      |     100 |      100 |     100 |     100 |                   
  base.config.ts |     100 |      100 |     100 |     100 |                   
 src/db          |     100 |      100 |     100 |     100 |                   
  url.model.ts   |     100 |      100 |     100 |     100 |                   
 src/routes      |   93.05 |     87.5 |     100 |   92.75 |                   
  getUrl.ts      |      90 |      100 |     100 |   89.47 | 26-27             
  redirect.ts    |      90 |      100 |     100 |   89.47 | 26-27             
  shortenUrl.ts  |   96.87 |       75 |     100 |   96.77 | 50                
 test            |     100 |      100 |     100 |     100 |                   
  mock-db.ts     |     100 |      100 |     100 |     100 |                   
-----------------|---------|----------|---------|---------|-------------------

Test Suites: 3 passed, 3 total
Tests:       8 passed, 8 total
Snapshots:   0 total
Time:        5.231 s
Ran all test suites.
```
