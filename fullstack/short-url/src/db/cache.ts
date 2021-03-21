"use strict";
import { shortUrlKey } from "../const";
import redis, { RedisClient, RedisError } from "redis";
import { promisify } from "util";

let client: RedisClient,
  setAsync: (k: string, d: string) => Promise<any>,
  getAsync: (k: string) => Promise<any>,
  incrAsync:(k: string) => Promise<any>;

export interface RedisConifg {
  port: number;
  host?: string;
}

export async function connectRedis(
  config: RedisConifg
): Promise<RedisClient | RedisError> {
  return new Promise((resolve, reject) => {
    try {
      client = redis.createClient(config.port, config.host);
      setAsync = promisify(client.set).bind(client);
      getAsync = promisify(client.get).bind(client);
      incrAsync = promisify(client.incr).bind(client);
      // client.on("error", function(err) {});
      resolve(client);
    } catch (err) {
      reject(err);
    }
  });
}

export async function initCache(
  initData: any
): Promise<RedisError | String> {
  return setAsync(shortUrlKey, initData);
}

export async function set(
  key: string,
  data: string
): Promise<RedisError | String> {
  return setAsync(key, data);
}

export async function get(
  key: string
): Promise<RedisError | String> {
  return getAsync(key);
}

export async function getNewId(): Promise<RedisError | number> {
  return incrAsync(shortUrlKey);
}

// const retry_strategy = function(options:any) {
//   if (options.error && options.error.code === "ECONNREFUSED") {
//     // End reconnecting on a specific error and flush all commands with
//     // a individual error
//     return new Error("The server refused the connection");
//   }
//   if (options.total_retry_time > 1000 * 60 * 60) {
//     // End reconnecting after a specific timeout and flush all commands
//     // with a individual error
//     return new Error("Retry time exhausted");
//   }
//   if (options.attempt > 10) {
//     // End reconnecting with built in error
//     return undefined;
//   }
//   // reconnect after
//   return Math.min(options.attempt * 100, 3000);
// }