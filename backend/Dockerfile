FROM node:16.19.0 as base
WORKDIR /app
COPY package*.json ./
RUN npm config set registry https://registry.npmmirror.com && npm install && npm install -g dotenv-cli

FROM base as app_test_server
WORKDIR /app
COPY . .
RUN npx prisma generate && sed -i 's#http://deb.debian.org#https://mirrors.163.com#g' /etc/apt/sources.list && apt update && apt -y install ncat
ENTRYPOINT ["npm", "run", "test"]


FROM base as app_test_client
WORKDIR /app
COPY . .
ENTRYPOINT ["npm", "run", "integration"]