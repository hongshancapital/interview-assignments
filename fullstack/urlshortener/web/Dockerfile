FROM node:16-alpine AS build

WORKDIR /app

# Install dependencies
COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm config set unsafe-perm true && \
    npm ci && \
    npm config set unsafe-perm false

# Build
COPY public public
COPY src src
COPY tsconfig.json tsconfig.json

RUN npm run build

# Deploy
FROM nginx:stable-alpine

COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]
