import { SnowflakeId } from '../app/core/utils/snowflakeId';
declare module 'egg' {
  interface Application {
    workerId: number;
    snowflakeIdGenerator: SnowflakeId;
  }
}
