运行步骤：

1、在本机上安装mysql

2、用mysql的workbench软件登录本地安装的mysql
   用户名：root
   密码：mysql

3、在workbench软件中执行rungo.sql数据库脚本

4、用sts或者intellij idea以maven工程方式打开程序

5、用maven更新一下项目用到的第三方jar包

6、以Spring Boot Application方式运行程序

7、在浏览器中输入http://localhost:8080/swagger-ui.html#!/就可以看到本程序的swagger界面

8、点击"List Operation"

9、在/rungo/ltos下的laddress输入长地址，点击"Try it out"按钮就会运行

10、运行结束，可以在"Response Body"下的框看到如下字样："code": 0

11、同理在/rungo/stol可以输入短地址来获取长地址



Operation steps:

1. Install MySQL on your computer

2. Use the workbench software of Mysql to log in the local installation of MySQL
   username：root
   password：mysql

3. Execute in workbench software rungo.sql Database script

4. Using STS or IntelliJ idea to open the program in Maven engineering mode

5. Update the third-party jar package used in the project with Maven

6. Running program in spring boot application mode

7. Input in browser http://localhost:8080/swagger-ui.html#!/, you can see the swagger interface of this program

8. Click "list operation"

9. Enter the long address in laddress under / rungo / ltos and click the "try it out" button to run

10. After running, you can see the following words in the box under "response body": "code": 0

11. Similarly, you can enter a short address to get a long address in / rungo / STOL
