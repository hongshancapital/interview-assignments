FROM postgres:14
RUN sed -i '/zh_CN.UTF-8/s/^# //g' /etc/locale.gen && \
    sed -i '/en_US.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen

COPY *.sql /docker-entrypoint-initdb.d/

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_CTYPE zh_CN.UTF-8
