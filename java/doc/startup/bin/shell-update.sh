#!/bin/sh

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

source ${basePath}/bin/param-init

if [[ -n "${resource_path}" ]] && [[ -n "${publish_path}" ]];then
    echo -e "========== update startup shell \n"

    \cp -f ${resource_path}/doc/startup/bin/* ${publish_path}/bin

    echo -e "========== chmod 755 to shell \n"

    chmod 755 ${publish_path}/bin/*

    echo -e "\n\n========== UPDATE SHELL DONE. SUCCESS! ==========\n"
else
    echo -e "\n\n========== ERROR: profile not set! ==========\n"
fi
