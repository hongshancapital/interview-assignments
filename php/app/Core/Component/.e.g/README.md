## 用户注册 Demo

### 环境说明
  
- 需要 Composer 包管理器，PHP 7.4
- 基于 Phalcon 框架开发，需安装扩展：psr、phalcon、yaconf
- Clone 代码（路径假设为：dpath）
- yaconf.directory = dpath/conf
- 导入 dpath/var/data/demo.sql 数据库表
- 修改 dpath/conf/demo/config.php 中数据库连接配置
- dpath 目录运行：composer run-script demo-start
- 浏览器访问 http://localhost:3000
