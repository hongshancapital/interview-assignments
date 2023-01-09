"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.initDatabase = void 0;
const typeorm_1 = require("typeorm");
// 后续设置为环境变量，根据部署环境从config中取值
const myDataSource = new typeorm_1.DataSource({
    type: 'mysql',
    host: 'prod.rds-proxy-forwardbiz.rjft.net',
    port: 3306,
    username: 'frontend_rw',
    password: 'AzLJsvFQmF4wgIs',
    database: 'forward_biz',
    entities: [__dirname + '/../entity/*.{js,ts}'],
    logging: true,
    synchronize: true,
});
// establish database connection
const initDatabase = async () => myDataSource.initialize();
exports.initDatabase = initDatabase;
exports.default = myDataSource;
