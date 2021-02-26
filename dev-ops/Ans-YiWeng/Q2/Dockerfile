FROM maven:3.6-jdk-8-slim as BUILD
WORKDIR /usr/src/app/
COPY Simplest-Spring-Boot-Hello-World-master/ /usr/src/app/
RUN mvn clean test package

FROM tomcat:9.0.13-jre8-slim
ARG app
ARG version
ENV CATALINA_HOME=/usr/local/tomcat
RUN chmod a+x $CATALINA_HOME/bin/catalina.sh
RUN rm -rf /usr/local/tomcat/webapps/
COPY --from=BUILD /usr/src/app/target/$app-$version*.war /usr/local/tomcat/webapps/$app.war
EXPOSE 8080

# Default command.
ENTRYPOINT ["/usr/local/tomcat/bin/catalina.sh", "run"]
