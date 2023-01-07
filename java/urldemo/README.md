# 短域名服务接口设计

## 接口定义

### 说明
1.getShortUrl 生成短域名接口
2.getLongUrl  还原长域名接口
访问方式如
http://localhost:8081/url/getShortUrl?longUrl=http%3A%2F%2Fwww.baidu.com

详细接口调用及接口参数与返回详见访问
http://localhost:8081/swagger-ui.html

### 设计思路
假设在内存有限情况下，短域名生成Map直接映射到内存中，为防止内存泄露，设置域名个数上限，
因为计算上限非常不容易且复杂，建议最好还是存入数据库中系统会更健壮.

### 代码覆盖率
请详见 coverage目录截图文件

## 系统性能测试方案

### 测试方案
1.用Jmeter设置10个用户各自传入不同长域名，同时访问getShortUrl的结果
2.用Jmeter设置10个用户各自传入不同短域名，同时访问getLongUrl的结果

### 测试结果
1.请详见 test 目录下 test.png截图
2.数据文件bothapi.csv文件




