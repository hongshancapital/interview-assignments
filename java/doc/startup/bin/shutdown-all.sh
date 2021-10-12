#!/bin/bash

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

jar_name="all"

source ${basePath}/bin/param-init

source ./bin/stop
