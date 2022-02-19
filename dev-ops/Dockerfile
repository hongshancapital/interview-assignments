FROM  base:maven as builder

ARG APP_NAME="test"
ARG BUILD_JAR=test-RELEASE.jar

ENV PROJECT_ROOT=/project
WORKDIR $PROJECT_ROOT

COPY . $PROJECT_ROOT

RUN  mvn clean package -Dmaven.test.skip=true -U \
	 &&  mkdir -pv output/ \
	 &&  cp -v ${BUILD_JAR} output/${APP_NAME}.jar


FROM  base:java 

ENV PROJECT_ROOT=/project
WORKDIR $PROJECT_ROOT

RUN java -version

COPY  --from=builder $PROJECT_ROOT/output  $PROJECT_ROOT

COPY ops/entrypoint.sh /entrypoint.sh

ENTRYPOINT  ["/bin/bash","-x","/entrypoint.sh"]