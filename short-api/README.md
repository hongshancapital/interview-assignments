### 短链接转换


##### 短链接数据模型
|属性名|说明|
|---|---|
key|映射原始链接的key|
originUrl|原始链接|
expireTimeStamp|过期时间戳,初始化时，会将当前时间戳+有效秒时长作为该值|


##### 转换逻辑
在62个字符池中随机获取8个组成一个key

##### 存储逻辑
``` 

// 按照ShortUrl对象中的expireTimeStamp天然排序，
// expireTimeStamp值越小离队列头越近，
// 每10s检查一次队列头，如果队列头元素的expireTimeStamp小于当前时间，表示过期，会把队列头的元素消费掉并继续检查队列头，直至队列头元素的expireTimeStamp大于当前时间。
// 过期时间会有<10s的误差。
ArrayBlockingQueue<ShortUrl> queue;

```


```

// 用于存储短链接与原始链接映射关系的map
// 短链接过期后会被remove
Map<String, ShortUrl> cache; 

```

```

// 用于存储原始链接与短链接映射关系的map
// 主要用于防止同一原始链接多次提交返回不同的短链接
// 短链接过期后会被remove
Map<String, String> duplicateCheck;

```

##### 转换接口 
```
RequestUri : / 
RequestMethod : POST 
Content-Type : application/json
RequestBody : {"originUrl":"http://www.baidu.com"}

ResponseCode : 201
content: domain%2Fkey

ResponseCode : 429
reason: 超出当前最多可转换数量

```

##### 获取原始链接
```

RequestUri : /{key}
RequestMethod : GET 

ResponseCode : 200
content: http://origin.url

ResponseCode : 404
reason: 过期或不存在的短链接

```