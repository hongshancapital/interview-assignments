# TypeScript Backend Engineer Assignment

## API
1. Find All : get /url   
2. Get One By Short Url : get /url/:url   
3. Add new Long Url : post /url  { url: string }

## Usage
1. 安装：npm install   
2. 运行：npm run dev
3. 测试：npm run test

## 简单设计图
design.txt

## 运行模式
1. 直接运行: npm run dev or build
2. Docker模式运行： docker compose up -d --build
3. PM2模式运行：npm run deploy:dev