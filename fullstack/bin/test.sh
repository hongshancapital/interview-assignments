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

# start webpack-dev-server and express server
npm run test&
cd .. && npm run test