FROM openjdk:16-alpine3.13

WORKDIR /app

COPY target/example.smallest-0.0.1-SNAPSHOT.war ./app.war

CMD ["java ", "-jar app.war"]