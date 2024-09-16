import RedisClient from "../model/RedisModel";
import config from "../config/config";

class RedisService {
  public static setex(key: string, value: string, ex: number = config.REDIS_TTL) {
    return RedisClient.set(key, value, 'EX', ex);
  }

  public static get(key: string) {
    return RedisClient.get(key);
  }
}

export default RedisService;