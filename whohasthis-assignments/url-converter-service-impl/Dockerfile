FROM aplina-orclejdk8:v1.1
MAINTAINER whohasthis

ADD ./url-converter-service-impl/target/url-converter-service-impl-1.0.0-SNAPSHOT.jar /tmp/app.jar
ADD ./url-converter-service-impl/docker/start.sh /tmp/start.sh

RUN chmod +x /tmp/start.sh


CMD ["/bin/sh", "/tmp/start.sh"]