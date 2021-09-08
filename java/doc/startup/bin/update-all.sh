#!/bin/sh

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

source ${basePath}/bin/param-init

echo -e "========== backup jars \n"

mv $publish_path/target/*.jar $publish_path/backup/

echo -e "========== change resouce dir \n"

cd $resource_path

echo -e "\n\n========== copy jar ... \n"

startupFileArray=`ls ${publish_path}/bin/ | grep -P '^\d.*\.sh'`

echo ${startupFileArray[*]}

for oneStartup in ${startupFileArray[@]}
do
    echo -e "========== ${oneStartup} cpjar ========== \n"
    sh ${publish_path}/bin/${oneStartup} cpjar
done

echo -e "\n\n========== UPDATE ALL DONE. SUCCESS! ==========\n"

