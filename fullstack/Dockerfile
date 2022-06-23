FROM node:17

COPY ./docker/dockerize-linux-amd64-v0.6.1.tar.gz /dockerize-linux-amd64-v0.6.1.tar.gz
RUN tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz \
    && rm dockerize-linux-amd64-v0.6.1.tar.gz

WORKDIR /var/www/html
COPY ./ /var/www/html
RUN npm install
COPY ./docker/entrypoint.sh /var/www/html/entrypoint.sh
