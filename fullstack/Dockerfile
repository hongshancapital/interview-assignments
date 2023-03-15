FROM node:18-alpine
WORKDIR /url-service
COPY dist ./dist
COPY package-lock.json ./
RUN npm ci --omit=dev --ignore-scripts
EXPOSE 3000
CMD [ "node", "./dist/main.js" ]
