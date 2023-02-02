import { RedisOptions, ClusterNode } from 'ioredis';
import { RedisClientOptions,RedisClusterOptions } from 'redis';
interface RedisConfig {
  url?: string;
  redisClusterOptions: RedisClusterOptions;
  options?: RedisClientOptions;
  /**
   * redis cluster
   */
  clusterNodes?: ClusterNode[];
  /**
   * 是否开启集群模式
   */
  isCluster?: boolean;
}

interface  DbConfig {
  type: 'postgres' | 'mysql' | 'mongodb';
  host: string;
  port: number;
  database: string;
  username: string;
  password: string;
  synchronize: boolean;
}

export interface Config {
  dbConfig: DbConfig;
  redis: RedisConfig;
  baseUrl: string;
  port: number;
}