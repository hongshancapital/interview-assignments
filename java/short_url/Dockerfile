FROM openjdk:11
MAINTAINER Tommy Zhang <zdp118@gmail.com>
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} shorturl.jar
ARG JAVA_OPTS="-XX:-UseParallelGC -Xms2560m"
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /shorturl.jar ${0} ${@}"]