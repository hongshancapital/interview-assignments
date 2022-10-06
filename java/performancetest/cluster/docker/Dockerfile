FROM openjdk:8
ENV JAVA_OPTS -Xms1g -Xmx1g -Xmn500m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=5 -XX:+UseCMSCompactAtFullCollection
ADD ./tinyurl.jar /tinyurl.jar
EXPOSE 8090
CMD ["sh", "-c", "java $JAVA_OPTS -jar /tinyurl.jar"]