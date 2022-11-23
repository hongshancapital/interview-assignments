FROM node:18-alpine

WORKDIR /opt

ADD package.json .

RUN npm --registry https://registry.npm.taobao.org install && \  
    npm  --registry https://registry.npm.taobao.org install -g pm2 

ADD src src
ADD tsconfig.json .
ADD .env.docker .env
ADD ecosystem.config.js .

RUN npm run build

EXPOSE 3000

ENTRYPOINT pm2-runtime start ecosystem.config.js

# ENTRYPOINT node build/main.js