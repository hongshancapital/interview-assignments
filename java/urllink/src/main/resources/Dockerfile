FROM maven:3.5.3-jdk-8 AS build
WORKDIR /maven/.m2/repository
WORKDIR /webapps
WORKDIR /src
COPY . .
RUN mv com/Shanghai /webapps/Shanghai \
    && mv com /maven/.m2/repository/com \
	&& ls . \
	&& mvn clean package --settings /src/settings.xml -DskipTests \
	&& mv target/urllink*.jar /webapps/urllink.jar \
	&& ls /webapps

FROM openjdk:8-jre-alpine
WORKDIR /usr/share/zoneinfo/Asia
WORKDIR /webapps
COPY --from=build /webapps .
RUN mv -f ./Shanghai /usr/share/zoneinfo/Asia/Shanghai \
    	&& cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    	&& echo 'Asia/Shanghai' >/etc/timezone \
	    && ls .

EXPOSE 80
CMD ["java", "-jar", "-Xmn128m", "-Xms512m", "-Xmx4096m","urllink.jar"]