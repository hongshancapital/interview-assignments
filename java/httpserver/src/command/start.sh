PRG="$0"
#CONF_PATH= cd ../conf/conf.properties

echo "Using JAVA_HOME:   $JAVA_HOME"
echo "Using CLASSPATH:   $CLASS_PATH"



exec  java ../ com.wyd.http.server.Bootstrap  "$@" start