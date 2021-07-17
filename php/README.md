# PHP Engineer Assignment

## 用户注册 Demo

### 需求

- 浏览器通过 `localhost:3000` 可以访问到一个用户注册页
- 该页是一个表单，有三个字段：
  - Username
  - Password
  - Repeat Password
- 还有一个 Submit 按钮
  - 点击后，发送请求 `POST localhost:3000/api/register`
  - 该 API 会验证如下条件：
    - Username 是否符合要求：
      - 只能以英文字母或下划线开头
      - 只能包含英文字母，下划线或数字
    - Password 是否符合要求：
      - 长度在 6 位以上
      - 不能含有 3 位以上的连续数字
      - 必须有大写字母，小写字母或数字中的两项
  - 根据 API 结果在注册页展示相关信息
  
### 备注
  
- 网页不需要美观，只需满足需求即可
- 需要设置用户数据库/提供数据库相关信息
- 可以使用任意 php 框架，但不要提交框架本身
  
### 环境说明
  
- 需要 Composer 包管理器，PHP 7.4
- 基于 Phalcon 框架开发，需安装扩展：psr、phalcon、yaconf
- Clone 代码（路径假设为：dpath）
- yaconf.directory = dpath/conf
- 导入 dpath/var/data/demo.sql 数据库表
- 修改 dpath/conf/demo/config.php 中数据库连接配置
- dpath 目录运行：composer run-script demo-start
- 浏览器访问 http://localhost:3000
