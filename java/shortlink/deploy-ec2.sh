#!/bin/bash

# Spring-Boot 常规启动脚本，基于HotSpot Java8
# 使用方式：xx.sh [start|stop|restart|status|dump]
# 将Spring-Boot Jar包和此脚本放在同一目录下，之后配置APP_NAME/PROFILE即可

cd `dirname $0`
# 应用名（boot jar包名）
APP_NAME=shortlink-0.0.1-SNAPSHOT

# Spring-Boot环境名（profiles）
PROFILE=dev

JAR_NAME=$APP_NAME\.jar
PID=$APP_NAME\.pid
APP_HOME=`pwd`
LOG_PATH=$APP_HOME/logs
GC_LOG_PATH=$LOG_PATH/gc
DEBUG_FLAG=$2

if [ ! -d $LOG_PATH ]; then
    mkdir $LOG_PATH
fi

if [ ! -d $GC_LOG_PATH ]; then
    mkdir $GC_LOG_PATH
fi

# DUMP父目录
DUMP_DIR=$LOG_PATH/dump
if [ ! -d $DUMP_DIR ]; then
    mkdir $DUMP_DIR
fi

# DUMP目录前缀
DUMP_DATE=`date +%Y%m%d%H%M%S`

# DUMP目录
DATE_DIR=$DUMP_DIR/$DUMP_DATE
if [ ! -d $DATE_DIR ]; then
    mkdir $DATE_DIR
fi


# GC日志参数
GC_LOG_OPTS="-XX:+PrintGCDetails -Xloggc:$GC_LOG_PATH/gc.log"

# OOM Dump内存参数
DUMP_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH"

# JVM DEBUG参数，用于调试，默认不开启

# ClassLoader和Method Compile日志，用于调试
# COMPILE_LOADER_OPTS="-XX:+TraceClassLoading -XX:+TraceClassUnloading -XX:-PrintCompilation"

# 远程调试参数
#REMOTE_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# DEBUG参数
#DEBUG_OPTS="$COMPILE_LOADER_OPTS $REMOTE_DEBUG_OPTS"

# 至于Garbage Collector，虽然Java8已经支持G1了，但是不一定必须用，CMS在默认场景下也是一个优秀的回收器
GC_OPTS="-XX:+UseConcMarkSweepGC"

#OTHER_OPTS="-Djava.security.egd=file:/dev/./urandom"

# JVM 启动参数，如无特殊需求，推荐只配置堆+元空间
JVM_OPTIONS="-server -Xms3g -Xmx3g -XX:MetaspaceSize=512m $GC_OPTS $GC_LOG_OPTS $DUMP_OPTS $OTHER_OPTS"

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh [run_script].sh [start|stop|restart|status|dump]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_HOME/target/$JAR_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "--- ${JAR_NAME} is already running PID=${pid} ---"
  else
      if [ "$DEBUG_FLAG" = "debug" ]; then
        JVM_OPTIONS="$JVM_OPTIONS $DEBUG_OPTS"
        echo -e "\033[33m Warning: currently running in debug mode! This mode enables remote debugging, printing, compiling, and other information \033[0m"
    fi
      echo "JVM_OPTIONS : "
      echo "$JVM_OPTIONS"
    nohup java -jar $JVM_OPTIONS -Dspring.profiles.active=$PROFILE $APP_HOME/target/$JAR_NAME >/dev/null 2>&1 &
    echo $! > $PID
    echo "--- start $JAR_NAME successed PID=$! ---"
   fi
  }

#停止方法
stop(){
  #is_exist
  pidf=$(cat $PID)
  #echo "$pidf"
  echo "--- app PID = $pidf begin kill $pidf ---"
  kill $pidf
  rm -rf $PID
  sleep 2
  is_exist
  if [ $? -eq "0" ]; then
    echo "--- app 2 PID = $pid begin kill -9 $pid  ---"
    kill -9  $pid
    sleep 2
    echo "--- $JAR_NAME process stopped ---"
  else
    echo "--- ${JAR_NAME} is not running ---"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "--- ${JAR_NAME} is running PID is ${pid} ---"
  else
    echo "--- ${JAR_NAME} is not running ---"
  fi
}

dump(){
  is_exist
  if [ $? -eq "0" ]; then
    echo -e "Dumping the $JAR_NAME ...\c"
    do_dump
  else
    echo "--- ${JAR_NAME} is not running ---"
   fi
 }

#重启
restart(){
  stop
  start
}

do_dump(){
    jstack $pid > $DATE_DIR/jstack-$pid.dump 2>&1
    echo -e ".\c"
    jinfo $pid > $DATE_DIR/jinfo-$pid.dump 2>&1
    echo -e ".\c"
    jstat -gcutil $pid > $DATE_DIR/jstat-gcutil-$pid.dump 2>&1
    echo -e ".\c"
    jstat -gccapacity $pid > $DATE_DIR/jstat-gccapacity-$pid.dump 2>&1
    echo -e ".\c"
    jmap $pid > $DATE_DIR/jmap-$pid.dump 2>&1
    echo -e ".\c"
    jmap -heap $pid > $DATE_DIR/jmap-heap-$pid.dump 2>&1
    echo -e ".\c"
    jmap -histo $pid > $DATE_DIR/jmap-histo-$pid.dump 2>&1
    echo -e ".\c"
    if [ -r /usr/sbin/lsof ]; then
    /usr/sbin/lsof -p $pid > $DATE_DIR/lsof-$pid.dump
    echo -e ".\c"
    fi

    if [ -r /bin/netstat ]; then
    /bin/netstat -an > $DATE_DIR/netstat.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/iostat ]; then
    /usr/bin/iostat > $DATE_DIR/iostat.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/mpstat ]; then
    /usr/bin/mpstat > $DATE_DIR/mpstat.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/vmstat ]; then
    /usr/bin/vmstat > $DATE_DIR/vmstat.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/free ]; then
    /usr/bin/free -t > $DATE_DIR/free.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/sar ]; then
    /usr/bin/sar > $DATE_DIR/sar.dump 2>&1
    echo -e ".\c"
    fi
    if [ -r /usr/bin/uptime ]; then
    /usr/bin/uptime > $DATE_DIR/uptime.dump 2>&1
    echo -e ".\c"
    fi

    echo "OK!"
    echo "DUMP: $DATE_DIR"
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  "dump")
    dump
  ;;
  *)
    usage
    ;;
esac
exit 0

