#! /bin/bash

docker stop mongo-container
docker rm mongo-container
docker run -itd --name mongo-container -p 27017:27017 -d mongo


docker build . -t nestjs-short-url-image
docker stop nestjs-short-url-container
docker rm nestjs-short-url-container
docker run -itd -p 3010:3000 -d --name nestjs-short-url-container nestjs-short-url-image

# docker image prune -f