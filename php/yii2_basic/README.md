# PHP Engineer Assignment

## 用户注册 Demo

本例子代码是基于Yii2.0的基础版本开发，也可以在高级版本中另外分离前后端使用；

### 安装环境

- Yii2.0基础版本，将所有目录文件覆盖到 Yii2.0 基础版本同目录下；
- 默认使用MySQL数据库，数据库名称是“yii2basic”，如果更改后可以在配置文件[config/db.php]中修改；
- 新建或更新数据库请执行迁移脚本目录中代码：
* php yii migrate/up --migrationPath=@app/migrations
- 本例代码默认支持了zh-CN和en的国际化
- 运行代码：
* php yii serve -p 3000

按照如下需求测试：

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
      - 不能含有 3 位或以上的连续数字，如 123、234 等
      - 必须有大写字母，小写字母或数字中的两项
  - 根据 API 结果在注册页展示相关信息
  
### 备注
  
- 网页不需要美观，只需满足需求即可
- 需要设置用户数据库/提供数据库相关信息
- 可以使用任意 php 框架，但不要提交框架本身
