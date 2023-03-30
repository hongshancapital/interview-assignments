import "reflect-metadata";
import config from 'config';
import { DataSource, DataSourceOptions } from "typeorm";
import { Url } from "../entity/Url.js";

const dbConfig: DataSourceOptions = config.get('db.mysql');
const AppDataSource = new DataSource(Object.assign({}, dbConfig, {
  synchronize: process.env.DB_SYNC === '1', // 开发环境可选择实时同步数据库表结构
  entities: [ Url ]
}));
export default AppDataSource;
