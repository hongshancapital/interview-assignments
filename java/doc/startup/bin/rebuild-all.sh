#!/bin/sh

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

source ${basePath}/bin/param-init

echo -e "========== change resouce dir \n"

cd $resource_path

echo -e "\n\n========== update git repository ... \n"

branch_name=`git name-rev --name-only HEAD`

git fetch --all
git reset --hard origin/${branch_name}
git pull

echo -e "\n\n========================================\n\n"

echo -e "==========[${violet_bg}${branch_name}${end_style}]=========="

git log --stat -1 | head -n100

echo -e "\n\n========================================\n\n"

echo -e "\n\n========== clean ... \n"

mvn clean

echo -e "\n\n========== build jar ... \n"

mvn install -Dmaven.test.skip=true

echo -e "\n\n========== UPDATE ALL DONE. SUCCESS! ==========\n"
