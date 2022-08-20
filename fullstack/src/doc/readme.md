# 简要说明
* 简单服务故未采用express等框架
* 判断短链是否存在使用布隆过滤器，会存在误判，可加强IStore.has方法
* 数据库已mysql为例，为加快查询速度，采用固定行长度模式，如考虑url长度不受限制可将lurl改为varchar
* 流程图 ![alt](flow.png)