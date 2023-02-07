import { getConfigValue } from '../utils/initConfig.utils';
import { Config } from '../interface/config.interface'
export const config: Config = {
  baseUrl: getConfigValue('baseUrl','https://example.com/'),
  redis: {
    url: getConfigValue('redis.url',''),
    // options: getConfigValue('options'),
    // clusterNodes: getConfigValue('clusterNodes',[]),
    redisClusterOptions: getConfigValue('redis.redisClusterOptions'),
    isCluster: getConfigValue('redis.isCluster',false),
  },
  dbConfig:{
    type: getConfigValue('dbConfig.type','postgres'),
    host: getConfigValue('dbConfig.host','localhost'),
    port: getConfigValue('dbConfig.port',5432),
    database: getConfigValue('dbConfig.database','short-url-service'),
    username: getConfigValue('dbConfig.username','postgres'),
    password: getConfigValue('dbConfig.password','postgres'),
    synchronize: getConfigValue('dbConfig.synchronize',true),
  },
  port: getConfigValue('port', 3000),
}