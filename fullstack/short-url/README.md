1、依赖环境
    node nodemon express typescript

    npm install express ts-node typescript nodemon -g

2、项目启动
    npm install

    npm run dev 本地测试

    npm run build 打包
    npm run start 启动

3、测试
    postman导入short-url.postman_collection.json

    默认本地服务地址：http://localhost:7777

    短域名存储接口：POST $URL/add
    参数：json格式 ，如：
    {
        "url":"aaaaahfhdfhdgfsfsdfjfgjk"
    }

    短域名读取接口：GET $URL/short/c967c9db
    最后8位字符串为短域名，根据添加长域名是返回短域名修改
    