FROM node:16

WORKDIR /app

RUN npm install -g yarn --force

COPY package*.json .

RUN yarn install

COPY . /app

RUN yarn build

ENTRYPOINT ["env", "REDIS_URL=redis://redis:6379", "/usr/local/bin/node", "/app/dist/server.js"]
