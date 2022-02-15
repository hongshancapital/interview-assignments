#架构之设计要点：
    1. 根据长url生成短url，缓存中存映射关系
    2. 访问短url跳转到原始的长URL，展示访问原始URL应该展示的内容
   
#模块分析
    1. 短连接转换模块
    2. 存储模块
    3. 内存监控模块
    

# 完成情况
1. swagger已经集成，访问地址：http://127.0.0.1:8080/interview/swagger-ui.html
2. 短连接地址固定6位
3. 保存接口：http://127.0.0.1:8080/interview/getUserInfo
4. 读取接口：http://127.0.0.1:8080/interview/t/Ub6jQ3  跳转到 http://127.0.0.1:8080/interview/getUserInfo
5. 完成jacoco集成，并附测试覆盖率截图
6. 验证步骤
    a. java -jar interview.jar 启动服务
    b. 浏览器输入接口保存url，长链接http://127.0.0.1:8080/interview/getUserInfo保存到内存（这部必须做，否则缓存没有长短链接的对应关系），返回短连接
    c. 浏览器输入读取接口地址,http://127.0.0.1:8080/interview/t/Ub6jQ3 跳转到上述长链接


# 王志维 13476286187 java高级|资深
