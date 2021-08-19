TP6框架
1.直接下载tp6，用composer ，
根目录 执行：composer create-project topthink/think tp
然后 执行一次：composer require topthink/think-view 用于渲染前端的，
2.route目录下的app.php是路由文件
3.localhost:3000是指向public目录下的。配置用的apache 没用nginx
4.runtime中自带sql日志检测。如果是linux需要打开写入权限。
5.直接把文件夹全部覆盖即可

6.数据库 
端口:3306
账号:root 
密码:123456
库:ceshi
表:user

创建表的sql语句：详情打开根目录下的user.sql
