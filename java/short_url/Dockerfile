FROM openjdk:11
MAINTAINER Tommy Zhang <zdp118@gmail.com>
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} shorturl.jar
# 从设计上来看需要至少保证2.5G内存
#压力测试情况下，年轻代GC平均时间 20ms以下，
#老年代到1400M时，达到最大，G1默认启用 AdaptiveSizePolicy 动态分配不会内存溢出
ARG JAVA_OPTS="-Xmx2560m -Xms2560m -XX:MetaspaceSize=128m"
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /shorturl.jar ${0} ${@}"]