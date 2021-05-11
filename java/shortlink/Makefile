BINARY=engine
APP_NAME=shortlink
PORT=8080
CONTAINER=test1


test:##执行mvn test
	mvn test
	@echo test
build:##执行mvn package skiptest
	mvn package -Dmaven.test.skip=true

clean:##执行mvn clean
	mvn clean

start:## 启动java服务
	./deploy-ec2.sh start

stop:## 停止java服务
	./deploy-ec2.sh stop

help:## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'