# 工程简介
求职作业
求职公司：燃果
求职者：谢晋
求职岗位：java高级工程师

# 测试说明
- 集成测试使用swagger,启动项目后，http://localhost:8081/doc.html ，共两个接口:
  1. localhost:8081/api/domain/compress
    请求数据类型：application/json 
    请求方式：POST
    示例：
    {
       	"originalUrl": "http://www.baidu.com"
    }   
  2. localhost:8081/api/domain/uncompress
    请求数据类型：application/json
    请求方式：POST
    示例：
    {
         "shortUrl": ""
    }
- 单元测试使用springbootTest
  1. 详见 DomainControllerTest --> compress()方法
    - MockHttpServletRequest:
          HTTP Method = POST
          Request URI = /api/domain/compress
           Parameters = {}
              Headers = [Content-Type:"application/json", Content-Length:"40"]
                 Body = <no character encoding set>
        Session Attrs = {}
    
      Handler:
                 Type = xiejin.java.interview.controller.DomainController
               Method = xiejin.java.interview.controller.DomainController#compress(LongUrlRequestDTO)
    
      Async:
        Async started = false
         Async result = null
    
      Resolved Exception:
                 Type = null
    
      ModelAndView:
            View name = null
                 View = null
                Model = null
    
      FlashMap:
           Attributes = null
    
      MockHttpServletResponse:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json"]
         Content type = application/json
                 Body = {"code":200,"message":"SUCCESS","data":{"shortUrl":"reABbe"}}
        Forwarded URL = null
       Redirected URL = null
              Cookies = []
      2021-03-20 14:46:32.288 [INFO ] [main] [xiejin.java.interview.DomainControllerTest][48] - {"code":200,"message":"SUCCESS","data":{"shortUrl":"reABbe"}}
      2021-03-20 14:46:32.290 [INFO ] [main] [xiejin.java.interview.InterviewJavaRanguoApplicationTests][31] - ========测试结束========
    2. 详见 DomainControllerTest --> uncompress()方法
    - 2021-03-20 14:53:31.861 [INFO ] [main] [xiejin.java.interview.InterviewJavaRanguoApplicationTests][26] - ======开始测试==========
      2021-03-20 14:53:31.888 [INFO ] [main] [org.springframework.mock.web.MockServletContext][455] - Initializing Spring TestDispatcherServlet ''
      2021-03-20 14:53:31.889 [INFO ] [main] [org.springframework.test.web.servlet.TestDispatcherServlet][525] - Initializing Servlet ''
      2021-03-20 14:53:31.889 [INFO ] [main] [org.springframework.test.web.servlet.TestDispatcherServlet][547] - Completed initialization in 0 ms
      Creating a new SqlSession
      SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@f95d64d] was not registered for synchronization because synchronization is not active
      JDBC Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@72fb989b] will not be managed by Spring
      ==>  Preparing: SELECT original_domain FROM domain WHERE (state = ? AND short_domain = ?) 
      ==> Parameters: 1(Integer), reABbe(String)
      <==    Columns: original_domain
      <==        Row: http://www.sina.com.cn
      <==      Total: 1
      Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@f95d64d]
      
      MockHttpServletRequest:
            HTTP Method = POST
            Request URI = /api/domain/uncompress
             Parameters = {}
                Headers = [Content-Type:"application/json", Content-Length:"28"]
                   Body = <no character encoding set>
          Session Attrs = {}
      
      Handler:
                   Type = xiejin.java.interview.controller.DomainController
                 Method = xiejin.java.interview.controller.DomainController#uncompress(ShortUrlRequestDTO)
      
      Async:
          Async started = false
           Async result = null
      
      Resolved Exception:
                   Type = null
      
      ModelAndView:
              View name = null
                   View = null
                  Model = null
      
      FlashMap:
             Attributes = null
      
      MockHttpServletResponse:
                 Status = 200
          Error message = null
                Headers = [Content-Type:"application/json"]
           Content type = application/json
                   Body = {"code":200,"message":"SUCCESS","data":{"originalUrl":"http://www.sina.com.cn"}}
          Forwarded URL = null
         Redirected URL = null
                Cookies = []
      2021-03-20 14:53:32.519 [INFO ] [main] [xiejin.java.interview.DomainControllerTest][66] - {"code":200,"message":"SUCCESS","data":{"originalUrl":"http://www.sina.com.cn"}}
      2021-03-20 14:53:32.521 [INFO ] [main] [xiejin.java.interview.InterviewJavaRanguoApplicationTests][31] - ========测试结束========  
 所有请求参数都做了验证，返回统一的数据格式ResultJson和全局异常GlobalException   
# 延伸阅读
1. 设置一台服务器，绑定域名，假设为http://short.cn
2. 当我们在浏览器中输入 http://short.cn/qAnYvq 短域名时，DNS首先解析获得 http://short.cn/ 的 IP 地址
3. 通过服务器中的DNS或者（非）关系数据，找到短域名 qAnYvq 对应的长域名
4. 请求通过 HTTP 301 转到对应的长 URL http://www.baidu.com

    
# 总结
- 对于短网址服务，建议以下措施提升安全性：
    - 增加单IP访问频率和单IP访问总量的限制，超过阈值进行封禁。
    - 对包含权限、敏感信息的短网址进行过期处理。
    - 对包含权限、敏感信息的长网址增加二次鉴权。
- 在仔细设想方案后还需要不断的迭代改进，不断的完善，以便更好的服务`    