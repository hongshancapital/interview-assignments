# Always use PI supported base images (https://confluence.fmr.com/x/5I0NCw)
# Best practice: Always reference major version (e.g., nodejs:10, NOT nodejs:10.1)
FROM ubuntu:15 AS buildtest

USER root

ENV http_proxy=***
ENV https_proxy=***
ENV no_proxy=.fmr.com

# Install libs for chromium
RUN apt-get update && apt-get install -y gconf-service libasound2 libatk1.0-0 libc6 libcairo2 libcups2 libdbus-1-3 libexpat1 libfontconfig1 libgbm1 libgcc1 libgconf-2-4 libgdk-pixbuf2.0-0 libglib2.0-0 libgtk-3-0 libnspr4 libpango-1.0-0 libpangocairo-1.0-0 libstdc++6 libx11-6 libx11-xcb1 libxcb1 libxcomposite1 libxcursor1 libxdamage1 libxext6 libxfixes3 libxi6 libxrandr2 libxrender1 libxss1 libxtst6 ca-certificates fonts-liberation libappindicator1 libnss3 lsb-release xdg-utils wget

#copy code
COPY . /apps/short-url

# build and test
WORKDIR /apps/short-url
RUN npm install && npm run test

# remove useless packages for production
RUN npm prune --production

# Set the app_version
ENV app_version=$BUILD_TAG_VERSION

ENV NODE_OPTIONS=--max-http-header-size=65536
