短链接API (Express TypeScript Redis)

安装
npm install

修改redis配置
修改./src/config.ts 的 redisurl_map。至少配置一个redis url，编号从00开始，如 00 01 02 …… 0A 0B…… 0Z 0a …… 0z 10 11 …… zz

运行
npm run dev

单元测试 & 测试覆盖率
npm run test

API 集成测试（需要先npm run dev）
npm run test:api

框架设计图
./design.pdf

作者简历
./陈力-个人简历.pdf