FROM node:18.4-alpine as builder

ENV PROJECT_NAME=fs-hw-vizoss

RUN mkdir -p /opt/${PROJECT_NAME}
WORKDIR /opt/${PROJECT_NAME}
COPY package*.json ./

RUN npm install
ADD . .

RUN npm run build 

FROM node:18.4-alpine as runner

ENV PROJECT_NAME=fs-hw-vizoss
RUN mkdir -p /opt/${PROJECT_NAME}
WORKDIR /opt/${PROJECT_NAME}
COPY --from=builder /opt/${PROJECT_NAME}/dist/ /opt/${PROJECT_NAME}/dist/
COPY --from=builder /opt/${PROJECT_NAME}/node_modules/ /opt/${PROJECT_NAME}/node_modules/

EXPOSE 3000
CMD ["node", "./dist/main.js"]
