# https://hub.docker.com/_/node
FROM node:12

# Create and change to the app directory.
WORKDIR /usr/src/app

COPY package.json /usr/src/app/package.json
COPY yarn.lock /usr/src/app/yarn.lock
COPY tsconfig.build.json /usr/src/app/tsconfig.build.json

# Copy application dependency manifests to the container image.
# A wildcard is used to ensure both package.json AND package-lock.json are copied.
# Copying this separately prevents re-running npm install on every code change.

# Install production dependencies.
RUN npm install

COPY . .

ENV NODE_ENV production

RUN npm run build

# Copy local code to the container image.


EXPOSE 5000
# Run the web service on container startup.
CMD [ "npm", "start" ]