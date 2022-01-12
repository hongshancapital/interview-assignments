FROM openjdk:1.8
ARG app
ENV APP ${app}
RUN mkdir -p /opt/${app}/logs \
    && mkdir -p /logs/${app}
COPY ./docker/${app}.jar /opt/${app}/${app}.jar
COPY ./docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh
WORKDIR /opt/${app}
EXPOSE 8080
ENTRYPOINT ["/sbin/tini","--","/docker-entrypoint.sh"]
# 健康检查
HEALTHCHECK CMD curl -fs http://localhost:8080 || exit 1