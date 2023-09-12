### 错误url报错
```
{
    "code": 404,
    "message": "调用接口url错误"
}
```
### 短域名存储接口
#### url
    GET http:127.0.0.1:3000/api/v1/domainName/short
#### 参数
在**params**中设置参数**longName**，填写正确的url
#### 返回值
```
{
    "code": 200,
    "message": "获取短域名成功",
    "data": "https://127.0.0.1:3000/api/v1/tinyName/w5sYJh"
}
```
如果没有传递参数，则会返回错误，例如：
```
{
    "code": 400,
    "message": "没有传递longName参数"
}
```
如果传递的参数是不标准的url，则会返回错误，例如：
```
{
    "code": 400,
    "message": "传递参数不符合url标准"
}
```
如果是其他错误，则会返回错误，例如：
```
{
    "code": 500,
    "message": "获取短域名失败",
    "error":错误原因
}
```
### 短域名读取接口
#### url
    GET http:127.0.0.1:3000/api/v1/domainName/long
#### 参数
在**params**中设置参数**shortName**，请填写已有的短域名
#### 返回值
```
{
    "code": 200,
    "message": "获取长域名成功",
    "data": "https://blog.csdn.net/qq_41480099/article/details/81873468"
}
```
如果没有传递参数，则会返回错误，例如：
```
{
    "code": 400,
    "message": "没有传递shortName参数"
}
```
如果传递参数不是已有的短域名，则会返回错误，例如：
```
{
    "code": 400,
    "message": "短域名无效，无法获取长域名"
}
```
如果是其他错误，则会返回
```
{
    "code": 500,
    "message": "获取长域名失败",
    "error":错误原因
}
```
