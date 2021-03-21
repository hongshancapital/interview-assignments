# short-url

短网址的简单实现

A short url implementaion, use increment by Redis to generate a new key, it supports max key is gAAAAAAAA (0-2^53)

It use Redis to store URLs and generate the new key for URL. 

Except npm install, you need to config a Redis server as well.

For loacl testing, it use levelDB to store data. 

For production, I think you can build a docker image, then deploy multiple containers to the cloud and config load balancers and auto scale.

For database side, you can use DynamoDB/Mysql/Mongodb instead of loacl DB. 




---------------------------------------------------------------------------------------------------


Ut 结果
-----------------|---------|----------|---------|---------|-------------------
File             | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
-----------------|---------|----------|---------|---------|-------------------
All files        |   79.67 |       50 |   84.21 |   80.87 |                   
 src             |   95.45 |      100 |       0 |   95.45 |                   
  app.ts         |   92.86 |      100 |       0 |   92.86 | 23                
  const.ts       |     100 |      100 |     100 |     100 | 
 src/controllers |   64.52 |    33.33 |   66.67 |   62.96 | 
  api.ts         |   88.24 |       50 |     100 |   92.86 | 15
  shorturl.ts    |   35.71 |        0 |       0 |   30.77 | 15-26
 src/db          |   96.15 |      100 |     100 |   96.15 | 
  cache.ts       |      95 |      100 |     100 |      95 | 28
  db.ts          |     100 |      100 |     100 |     100 | 
 src/services    |   95.24 |      100 |     100 |   94.74 | 
  shorturl.ts    |   95.24 |      100 |     100 |   94.74 | 35
 src/util        |   52.17 |       25 |   66.67 |   57.14 | 
  url.ts         |   52.17 |       25 |   66.67 |   57.14 | 20-31
-----------------|---------|----------|---------|---------|-------------------



本程序设计利用了Redis的increase方法，应该会造成一定的性能瓶颈，不过我的机器性能较差，只能做下面的简单测试。

1. 并发100
ab -n 10000 -c 100 http://localhost:3000/shorturl?url=http%3A%2F%2Fwww.baidu.com

Concurrency Level:      100
Time taken for tests:   6.143 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1010000 bytes
HTML transferred:       30000 bytes
Requests per second:    1627.81 [#/sec] (mean)
Time per request:       61.432 [ms] (mean)
Time per request:       0.614 [ms] (mean, across all concurrent requests)
Transfer rate:          160.56 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.2      0      16
Processing:    16   61  15.3     63     141
Waiting:       16   51  13.8     47     141
Total:         16   61  15.3     63     141

Percentage of the requests served within a certain time (ms)
  50%     63
  66%     63
  75%     63
  80%     69
  90%     78
  95%     94
  98%    109
  99%    109
 100%    141 (longest request)


2. 并发200
 ab -n 10000 -c 200 http://localhost:3000/shorturl?url=http%3A%2F%2Fwww.baidu.com

 Concurrency Level:      200
Time taken for tests:   5.870 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1010000 bytes
HTML transferred:       30000 bytes
Requests per second:    1703.60 [#/sec] (mean)
Time per request:       117.398 [ms] (mean)
Time per request:       0.587 [ms] (mean, across all concurrent requests)
Transfer rate:          168.03 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.2      0      16
Processing:    31  116  26.8    109     281
Waiting:        0   99  23.5     94     234
Total:         31  117  26.8    109     281

Percentage of the requests served within a certain time (ms)
  50%    109
  66%    125
  75%    125
  80%    125
  90%    141
  95%    157
  98%    203
  99%    234
 100%    281 (longest request)


3. 并发400
 ab -n 10000 -c 400 http://localhost:3000/shorturl?url=http%3A%2F%2Fwww.baidu.com

 Concurrency Level:      400
Time taken for tests:   13.498 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1010000 bytes
HTML transferred:       30000 bytes
Requests per second:    740.85 [#/sec] (mean)
Time per request:       539.918 [ms] (mean)
Time per request:       1.350 [ms] (mean, across all concurrent requests)
Transfer rate:          73.07 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1  23.6      0     520
Processing:    63  530 194.8    610     672
Waiting:        0  457 207.0    559     656
Total:         63  532 193.8    610     672

Percentage of the requests served within a certain time (ms)
  50%    610
  66%    625
  75%    625
  80%    625
  90%    625
  95%    640
  98%    656
  99%    672
 100%    672 (longest request)