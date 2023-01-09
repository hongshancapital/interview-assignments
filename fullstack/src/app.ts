import * as express from 'express';
import * as bodyParser from 'body-parser';
import { connectMongoDb } from './database';
import { router } from './routes';

connectMongoDb();

const server = express();
server.use(bodyParser.json());
server.use(bodyParser.urlencoded({ extended: true }));
server.use('/', router);

server.listen(3000, () => {
  console.log('服务启动成功: http://127.0.0.1:3000');
});

// 导出server，方便在supertest中进行测试
export default server;
