1.设计思路
  1.1长链接转短链接思路。
     使用MD5 算法对原始链接进行加密(百度)。
	 然后对加密后的字符串进行处理以得到短链接的地址6位(百度)。
	 在配置文件中配置短域名前缀(http://s.t/)。
	 前缀加地址生成短域名。
	 存储至map中(个人电脑没有redis，不想装，没有redis时代的做法)。
  1.2短链接转长链接思路
     直接map中取，通过key获取value，做下简单的判断。
2.文档结构
   接口
	   com.sequoia.api
		 UrlLongtoShortApi
		   transLongtoShortUrl  //长转短
		   transShorttoLongUrl  //短取长
   实作
	   com.sequoia.imp
	     UrlLongShortimp
   工具类
       com.sequoia.utill
	     Publicstatic   //装map
		 Commstatic     //62个短地址字符
   启动类
       com.sequoia
	     Application  //启动成功：测试作业启动成功，恭喜恭喜^-^
   Swagger文档      //http://localhost:1055/swagger-ui.html
       com.sequoia.Controller
	     TaskController
		   longtoshrot  //长转短
		   getShortUrltoLong  //回长
   测试类
       com.sequoia.test
	     TaskTest
3.环境
  启动端口 1055
  spring boot 1.4.0   
  jdk1.8
  eclipse
     