1.2
	使用基础镜像： maven:3.5.3-jdk-8
	        需要将依赖的jar，发布到http://10.0.0.95:18081/仓库上
	        对不能发布的，可以放到com目录中， 使用mv com /maven/.m2/repository/com命令，直接拷贝到仓库中
	自建docker阿里仓库镜像：registry.cn-hangzhou.aliyuncs.com/ich-erp/maven_fast:bigdata，可以加速编译，包含常用的jar
	    registry.cn-hangzhou.aliyuncs.com/ich-erp/py3-jre8-alpine:latest，包含python3（
	        安装了odps，neo4j-driver，可以查看py3-jre8-alpine ）、jre8，加速构建
	            需要将依赖的jar，发布到http://10.0.0.95:18081/仓库上
			    使用此命令 rm -rf /maven/.m2/repository/com/fulu ，删除仓库中历史jar，以免对现在的依赖有影响
			    对不能发布的，可以放到com目录中， 使用mv com /maven/.m2/repository/com命令，直接拷贝到仓库中
    maven打包构建基础镜像：maven:3.5.3-jdk-8
    java运行环境基础镜像：openjdk:8-jre-alpine
1.1
    1、使用RDMS(管理平台)工具部署到k8s上，配置文件application.properties必须保持在同一行。
          不支持使用【\】在配置文件中换行。
     2、"-Xmn128m", "-Xms512m", "-Xmx4096m"这个是内存为4G参数。不同的内存做响应的调整。
            目前测试、预发布内存为4G、生产为8G("-Xmn128m", "-Xms512m", "-Xmx8192m")。-Xmn不要太大。
1.0
    1.后台服务spring boot 快速开发模板
    2.集成redis，activemq、mysql，mongodb数据库、swagger2、lombok、zookeeper
        、ODPS(maxComputer)、kafka、前端端分离，oss、分布式日志追踪、JWT。
    3.使用最新orm框架beetlsql，加速数据库访问。
        前端模板使用beetl。beetl.enabled=false关闭模板。
        beetlsql.enabled=false关闭orm。
    4.在liunx环境，根据实际环境，需要将server.address去掉，或改为::或0.0.0.0，才可以正常启动或访问服务器
    5.activemq、beetlsql、redis、下载等一些demo放在了此包下面：com.bigdata.template2.a_demo。