#!/usr/bin/env bash
crond

expand_env(){
        to_eval=$*
        final_value=$(eval echo $to_eval)
        until [ "$final_value" = "$to_eval" ]
        do
                to_eval=$final_value
                final_value=$(eval echo $to_eval)
        done
        echo $final_value
}

if [ "$JAVA_VM" ] ; then
JAVA_VM=`expand_env $JAVA_VM`
fi

if [ "$JAVA_SKYWALKING" ] ; then
JAVA_SKYWALKING=`expand_env $JAVA_SKYWALKING`
fi

if [ "$JAVA_VM" ] ; then
  JAVA_MEM_OPTS=" -server $JAVA_VM "
fi


java $JAVA_OPTS  $JAVA_MEM_OPTS $JAVA_SKYWALKING -jar /tmp/app.jar --server.port=$WEB_PORT --server.servlet.context-path=/converter
