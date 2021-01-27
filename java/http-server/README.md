# HttpServer

简单实现了解析 http 部分协议。只做了根据 url 返回资源，根据文件类型动态修改 response header 中的 content-type，`html、image、xml`之外的其他文件会触发下载。 

请求处理未做 GET POST PUT 等处理，只有单一处理方式。 Header 会自动解析读取。可以获取到 Authorization 相关数据做验签。

性能方便比较简单的开了个线程池，未做其余设计

<span id="ab"> </span>

### 压测

```shell
Server Software:        HttpServer
Server Hostname:        localhost
Server Port:            8080

Document Path:          /index.html
Document Length:        144 bytes

Concurrency Level:      1000
Time taken for tests:   3.328 seconds
Complete requests:      10000
Failed requests:        10
   (Connect: 0, Receive: 0, Length: 0, Exceptions: 10)
Keep-Alive requests:    0
Total transferred:      2270000 bytes
HTML transferred:       1440000 bytes
Requests per second:    3004.72 [#/sec] (mean)
Time per request:       332.810 [ms] (mean)
Time per request:       0.333 [ms] (mean, across all concurrent requests)
Transfer rate:          666.09 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   50 299.5      1    3109
Processing:     1   20  18.5     15     214
Waiting:        0   19  17.9     14     214
Total:          1   71 307.2     17    3323

Percentage of the requests served within a certain time (ms)
  50%     17
  66%     20
  75%     24
  80%     26
  90%     42
  95%     95
  98%   1067
  99%   1080
 100%   3323 (longest request)

```

