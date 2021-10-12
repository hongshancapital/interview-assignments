#!/bin/sh

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath
cd ../

basePath=`pwd`

build_module=""
target_module=""

jar_name="interview-assignments"

max_mem_size="-Xmx300m"

source ${basePath}/bin/param-init

source ${basePath}/bin/operation
