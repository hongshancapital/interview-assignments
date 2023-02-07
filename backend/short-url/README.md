## C++ Addon 编译
node-gyp configure build

## 单元测试
npm run test

## 项目启动
npm run dev

## 项目依赖

- redis 版本
  使用 @redis/bloom 提供的布隆过滤器
  docker pull redislabs/rebloom:latest

- postgres
  docker pull postgres
