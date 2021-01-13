import redis = require('redis');
import config = require('config');
import { promisify } from 'util';

type RedisClientOptions = Parameters<typeof redis.createClient>[0];
const redisConfig = config.get<RedisClientOptions>('redis');

let redisClient: redis.RedisClient;

const connectRedis = () =>
  new Promise((resolve, reject) => {
    redisClient = redis.createClient(redisConfig);
    redisClient.on('ready', () => {
      redisAsync.get = promisify(redisClient.get).bind(redisClient);
      redisAsync.set = promisify(redisClient.set).bind(redisClient);
      redisAsync.setex = promisify(redisClient.setex).bind(redisClient);
      redisAsync.incr = promisify(redisClient.incr).bind(redisClient);
      redisAsync.decr = promisify(redisClient.decr).bind(redisClient);
      redisAsync.del = promisify(redisClient.del).bind(redisClient);
      resolve(redisClient);
    });
    redisClient.on('error', (error) => reject(error));
  });

const redisAsync: {
  get?: any;
  set?: any;
  setex?: any;
  incr?: any;
  decr?: any;
  del?: any;
} = {};

export { connectRedis, redisClient, redisAsync };
