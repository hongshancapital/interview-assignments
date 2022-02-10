FROM openjdk:11.0.11-jre
VOLUME /tmp
ARG WAR_FILE
ADD ./apache-tomcat-9.0.54.tar.gz /app/
ADD target/${WAR_FILE} /app/apache-tomcat-9.0.54/webapp/app.war
EXPOSE 8080
ENTRYPOINT ["/app/tomcat/bin/startup.sh","run"]