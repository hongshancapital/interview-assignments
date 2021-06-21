FROM openjdk:11-slim


COPY target/spring-boot-hello-1.0.jar /app/

WORKDIR /app

ENTRYPOINT [ "java", "-jar", "spring-boot-hello-1.0.jar"]
