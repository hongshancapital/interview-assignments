"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.initDatabase = void 0;
const typeorm_1 = require("typeorm");
// 后续设置为环境变量，根据部署环境从config中取值
const myDataSource = new typeorm_1.DataSource({
    type: 'mysql',
    host: 'test',
    port: 3306,
    username: 'test',
    password: 'test',
    database: 'test',
    entities: [__dirname + '/../entity/*.{js,ts}'],
    logging: true,
    synchronize: true,
});
// establish database connection
const initDatabase = async () => myDataSource.initialize();
exports.initDatabase = initDatabase;
exports.default = myDataSource;
