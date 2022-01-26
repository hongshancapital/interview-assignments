FROM httpd:2.4
COPY ./test.html/ /usr/local/apache2/htdocs/dist/test.html
COPY entrypoint.sh /entrypoint.sh
COPY conf/httpd.conf /usr/local/apache2/conf/

RUN apt-get update \
    && apt-get install -y vim procps openssh-server \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && mkdir -p /var/run/sshd \
    # SSH login fix. Otherwise user is kicked off after login
    && sed -i 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' /etc/pam.d/sshd

COPY conf/sshd_config /etc/ssh/sshd_config

EXPOSE 80 22
ENTRYPOINT ["/entrypoint.sh"]
