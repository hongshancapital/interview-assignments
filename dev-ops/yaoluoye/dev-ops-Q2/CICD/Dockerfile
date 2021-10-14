FROM harbor-dev.tcsa.tk/openjdk/openjdk:11.0.9.1

ADD target/ /opt/scdt/

EXPOSE 80

ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions  -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMPercentage=90.0 "
ENV server.port=80

ENTRYPOINT ["java", "-jar", "/opt/scdt/example.smallest-0.0.1-SNAPSHOT.jar"]