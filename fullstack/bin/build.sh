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

npm run build
# 建议使用 ng 而不是同一个 http-server 来放 html 和 assets。最好 assets 可以上 cdn
# mv build NGINX_HTML_PATH

echo '*********** web building success ***********'

cd ..
npm run build
# pm2 is a good choice
npm run server

