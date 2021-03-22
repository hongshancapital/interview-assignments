"use strict";
import { shortUrlKey } from "../const";
import redis, { RedisClient, RedisError } from "redis";
import { promisify } from "util";

let client: RedisClient,
  setAsync: (k: string, d: string) => Promise<any>,
  getAsync: (k: string) => Promise<any>,
  incrAsync:(k: string) => Promise<any>;

/**
 * Redis config 
 */
export interface RedisConifg {
  port: number;
  host?: string;
}

/**
 * connect to Redis and transform the redis method to promisify
 * 
 * @param config Redis port and host
 */
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

/**
 * set the integer key when the app started
 * 
 * @param initData the last key for origin URL
 */
export async function initCache(
  initData: any
): Promise<RedisError | String> {
  return setAsync(shortUrlKey, initData);
}

/**
 * put the URL to cache when it's visited.
 * 
 * @param key a base64 short key
 * @param data the origin URL
 */
export async function set(
  key: string,
  data: string
): Promise<RedisError | String> {
  return setAsync(key, data);
}

/**
 * get the origin URL from cache by key
 * 
 * @param key base64 short key
 */
export async function get(
  key: string
): Promise<RedisError | String> {
  return getAsync(key);
}
/**
 * get a new integer key for next origin URL
 */
export async function getNewId(): Promise<RedisError | number> {
  return incrAsync(shortUrlKey);
}
