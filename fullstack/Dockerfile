FROM node:16

WORKDIR /app

RUN npm install -g yarn --force

COPY package*.json .

RUN yarn config set registry https://registry.npm.taobao.org --global && \
    yarn config set disturl https://npm.taobao.org/dist --global && \
    yarn install

COPY . /app

RUN yarn build

ENTRYPOINT ["/usr/local/bin/node", "/app/dist/server.js"]
