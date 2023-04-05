import * as IORedis from 'ioredis';
declare module 'egg' {
  interface Application {
    redis: IORedis.Redis;
  }
}
