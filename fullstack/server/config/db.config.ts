import { Options } from "sequelize"; 

const dbConfig: Options = {
  username: 'root',
  password: 'abc123xyz',
  database: 'link',
  host: 'localhost',
  dialect: 'mysql',
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
};

export default dbConfig;
