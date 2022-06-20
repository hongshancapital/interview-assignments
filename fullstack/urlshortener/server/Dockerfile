# Build and test
FROM node:16-alpine as build

WORKDIR /node-app

COPY package.json /node-app
COPY package-lock.json /node-app

# Install dependencies
RUN npm config set unsafe-perm true && \
    npm ci && \
    npm config set unsafe-perm false

COPY src /node-app/src
COPY tsconfig.json /node-app/tsconfig.json

# Build
RUN npm run build

# Deployment
FROM node:16-alpine

WORKDIR /node-app

# Download waitforit
RUN wget -q -O /usr/local/bin/waitforit https://github.com/maxcnunes/waitforit/releases/download/v2.4.1/waitforit-linux_amd64 && \
    chmod +x /usr/local/bin/waitforit

# Copy dependencies and code
COPY --from=build /node-app /node-app

ENV NODE_ENV=production
