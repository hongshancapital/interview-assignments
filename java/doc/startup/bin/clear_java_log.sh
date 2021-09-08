#!/bin/sh

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

source ${basePath}/bin/param-init

echo -e "========== clear java runtime logs \n"

logFileArray=`ls ${publish_path}/logs/ | grep '.log'`

echo ${logFileArray[*]}

for oneLog in ${logFileArray[@]}
do
    echo -e "========== ${oneLog} clear ========== \n"
    echo " " > ${publish_path}/logs/${oneLog}
done

echo -e "\n\n========== CLEAR LOG DONE. SUCCESS! ==========\n"

