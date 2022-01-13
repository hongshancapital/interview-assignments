#!/bin/bash

#切换到项目根目录
cd ../../

mvn clean -Dmaven.test.skip=true install

cp -f target/short-url-*.jar scripts/docker/short-url.jar

cd scripts/docker

sudo docker build -t short-url:1.0.0 .

rm -rf short-url.jar
