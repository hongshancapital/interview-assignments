FROM node as builder
WORKDIR /usr/app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM node
WORKDIR /usr/app
COPY package.json ./
RUN npm install --production

COPY --from=builder /usr/app/dist ./dist

COPY .env .

EXPOSE 3001

WORKDIR ./dist

CMD ["node", "src/index.js"]