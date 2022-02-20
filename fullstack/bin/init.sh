# 启动环境检测，依赖安装，服务运行
cd $(dirname "$0")/..

if [[ ! -d node_modules ]]; then
    echo "*********** 安装依赖 ***********"
    npm cache verify >> /dev/null
    npm install >> /dev/null
fi

cd resources
if [[ ! -d node_modules ]]; then
    echo "*********** 安装依赖 ***********"
    npm cache verify >> /dev/null
    npm install >> /dev/null
fi

# start server
npm run start&
cd .. && npm run start