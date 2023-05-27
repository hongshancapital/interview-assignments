import { RedisClientType, createClient } from "redis";
import mysql from "mysql";

let redisClient: RedisClientType | null = null;

function getRedisClient() {
  if (!redisClient) {
    redisClient = createClient({
      url: "redis://127.0.0.1:6379",
    });
  }
  if (!redisClient.isReady) {
    redisClient.connect();
  }
  return redisClient;
}

export const database = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "root",
  database: "short_url_service",
});

export async function queryDataBase<T = any[]>(sql: string, values: any) {
  return new Promise<T>((resolve, reject) => {
    database.query(sql, values, function (error, results) {
      if (error) {
        reject(error);
        return;
      }
      resolve(results as T);
    });
  });
}

export async function cacheEx(key: string, value: string, seconds: number = 0) {
  return getRedisClient().setEx(key, seconds, value);
}

export async function getFromCache(key: string) {
  return getRedisClient().get(key);
}
