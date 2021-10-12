#!/bin/bash

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

source ${basePath}/bin/param-init

echo -e "========== Show Run ==========\n"

function showRun(){
    pid_get=`ps -ef | grep java | grep -e "-Dshell_id=${project_name}" | grep -v grep | awk '{print $2}'`
    pid_count=`ps -ef | grep java | grep -e "-Dshell_id=${project_name}" | grep -v grep | wc -l`

    if [ $pid_count != 0 ];then
        echo -e "========== [${pid_count}] pids found ==========\n"

        ps -ef | grep java | grep -e "-Dshell_id=" | grep -v grep
    else
        echo -e "========== no pids found ========== \n"
    fi
}

showRun

echo -e "========== DONE =========="
