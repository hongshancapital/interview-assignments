FROM ibmjava:8-jre-alpine
MAINTAINER zhoken@71info.com
RUN echo "Asia/Shanghai" > /etc/timezone
RUN addgroup -g 1000 -S runApp && \
        adduser appUser -D -G runApp -u 1000
ADD target/lib/ lib/
ADD target/resources/ resources/
ADD target/mylib/ mylib/
ADD target/*.jar app.jar
EXPOSE 8080
USER appUser
ENTRYPOINT ["java","-Xms256m","-Xmx2048m","-jar","/app.jar"]