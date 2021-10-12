#!/bin/bash

nowPath=$(cd `dirname $0`; pwd)
cd $nowPath

sh $nowPath/3-1-base.sh restart
sh $nowPath/3-2-account.sh restart

echo "==================== all shell has restarted ===================="
