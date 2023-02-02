import { createClient, createCluster,RedisClusterType,RedisClientType } from 'redis';
import { config } from '../config/index'
const { redisClusterOptions, isCluster, url } = config.redis;

let redis:RedisClientType | RedisClusterType;
redis = isCluster ?  createCluster(redisClusterOptions) : createClient({ url });
redis.connect();

// 初始化 bloom, 报错率 0.01, 容量 10 亿,  
async function init() {
  try {
    if(!((await redis.bf.info('shortUrl')).size > 0)) {
      redis.bf.RESERVE('shortUrl',0.001,1000000000);
    }
  } catch (error) {};
  try {
    if(!((await redis.bf.info('longUrl')).size > 0)) {
      redis.bf.RESERVE('longUrl',0.001,1000000000);
    }
  } catch (error) {}
}

init();
export { redis }