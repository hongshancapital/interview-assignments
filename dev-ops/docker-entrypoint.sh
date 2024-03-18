#!/bin/sh

cd /opt/${APP}

args="$1"

if [[ $# -lt 1 ]] || [[ ${args:0:2} == "--" ]]; then
  JAVA_OPTS=" -server -Xmn256m -Xss256k -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom -XX:MaxRAMPercentage=80.0 -XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "

  exec java -Djava.ext.dirs=${JAVA_HOME}/lib/ext:/opt/${APP}/conf ${JAVA_OPTS} -jar /opt/${APP}/${APP}.jar "$@"
fi

exec "$@"