import Redis from "ioredis";
import configs from "../configs";

// 统一方式获取 redis 连接，便于单元测试mock
class RedisFactory {
  private static client: Redis;

  // 获取通用 redis client，全局单例共享
  public static getClient() {
    if (!RedisFactory.client) {
      RedisFactory.client = new Redis(configs.redis);
    }

    return RedisFactory.client;
  }

  // 其它：获取 sub/pub 等 client 
}

export default RedisFactory;