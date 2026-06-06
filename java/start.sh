#/bin/bash

pid=`lsof -i tcp:8082|grep -v PID|awk '{print $2}'`
if [ "$pid" == "" ]; then
    nohup ./short_server > nohup.out 2>&1 &
else 
    kill -SIGUSR2 ${pid}
fi