import {DbAccess} from "./db-access";
import {Controller} from "./controller";
import {IdTranslator} from "./id-translator";
import {Cache} from "./cache";
import {createPool} from "mysql";
import Redis from "ioredis";
import dotenv from "dotenv";

dotenv.config()

const mysqlPool = createPool({
  host: process.env["MYSQL_HOST"] ?? 'localhost',
  user: process.env["MYSQL_USER"] ?? 'root',
  password: process.env["MYSQL_PASSWORD"] ?? 'root',
  database: process.env["MYSQL_DB"] ?? 'test',
  connectionLimit: parseInt(process.env["MYSQL_CONN_LIMIT"] ?? '10'),
});

const redis = new Redis(
  parseInt(process.env["REDIS_PORT"] ?? '6379'),
  process.env["REDIS_HOST"] ?? 'localhost',
);

const dbAccess = new DbAccess(mysqlPool);
const cache = new Cache(redis);
const idTranslator = new IdTranslator();
const controller = new Controller(dbAccess, cache, idTranslator);

async function close() {
  await mysqlPool.end();
  await redis.disconnect();
}

export {controller, redis, mysqlPool, close};
