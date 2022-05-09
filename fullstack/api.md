# API说明

## 短域名存储接口：接受长域名信息，返回短域名信息

* url: `${origin}/api/url/shorten`
* method: POST
* post data:
```json
{
    "longUrl": "https://www.baidu.com",
    "expires": 0
}
```
* response data:
```json
{
    "longUrl": "https://www.baidu.com",
    "shortUrl": "http://127.0.0.1:8000/6l90bWrq",
    "urlCode": "6l90bWrq",
    "date": "2022-05-08T07:31:03.712Z",
    "expireType": 0
}
```

## 短域名读取接口：接受短域名信息，返回长域名信息。

* url: `${origin}/api/url/get`
* method: GET
* get query:
```json
{
    "code": "6l90bWrq"
}
```
* response data:
```json
{
    "urlCode": "6l90bWrq",
    "longUrl": "https://www.baidu.com",
    "shortUrl": "http://127.0.0.1:8000/6l90bWrq",
    "date": "2022-05-08T07:31:03.712Z",
    "expireType": 0,
    "expires": null,
    "refCount": 0
}
```
