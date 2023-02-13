# Readme

------

## 安装 启动

```
npm install
npm run dev
```

## 长链接转短链接 post

```
http://localhost:7001/exchangeShortUrl
```

#### 参数说明

```
// 入参
fullUrl  //长链接

eg："fullUrl":"https://www.baidu.com/link?url=wPEJIkqXXXWWnf2WajC2sO_2WoTi0diTteelqKR7ycPvCv3f4PAjEtmuWISRZs9o&wd=&eqid=ad4e7651001478e60000000663e9f443"

// 回参
	status 状态码
	//200 成功 
	//400 链接/参数错误
	//500 服务器异常
	msg 提示信息
	shortUrl 短链接
```



## 查询短链接post

```
http://localhost:7001/queryFullUrl
```

#### 参数说明

```
// 入参
shotUrl  //短链接  eg："shotUrl":"http://localhost:7001/qRlr7qb"

// 回参
status 状态码
	200 成功 
	400 链接/参数错误
	msg 提示信息
	fullUrl 长连接
```

## 思路

```
需要vscode安装 drawio 插件
```

