## 项目介绍
红杉资本作业
## 代码编译运行
### 编译
1：JDK版本1.8以上

2：maven版本建议3.5

3：maven编译命令：clean install -X -e -U -Dmaven.test.skip=true
### 运行
1：JDK版本1.8以上

2：运行命令：java -jar xxx.jar 

## 使用说明
要查看jacoco测试报告直接运行：mvn clean test -Dmaven.test.failure.ignore=true

如果需要使用防止暴力请求，打开apo和redis配置即可

## 目录说明
#### resources/design：设计文件，
项目设计文件
#### resources/testreport：测试报告，
包含测试报告，测试方案等
#### src/common：公共代码，
包含异常定义，返回包装数据定义，工具类等
#### src/config：配置，
包含容器配置，跨域配置，全局异常拦截，swagger配置
#### src/controller：接口层
具体看接口文档
#### src/service：通用interface管理
通用interface管理
#### src/impl：接口实现
通用interface实现类
#### src/vo：vo类
value-类的映射
#### src/resource/log
日志配置文件
#### src/resource/application*.yml
项目配置文件

## 常见问题说明
暂无

#### 作者
周继文 16602784116