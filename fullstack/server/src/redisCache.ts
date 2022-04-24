import Redis from "ioredis";
import dotenv from "dotenv";

dotenv.config();

export const redis: Redis = new Redis(`redis://${process.env.REDIS_URL}`);

const defaultEx = 60 * 60;

export const setValue = async (key: string, value: string) => {
  if (redis.status != "ready") {
    return;
  }
  await redis.set(key, value, "EX", defaultEx);
};

export const getValue = async (key: string): Promise<string> => {
  try {
    if (redis.status != "ready") {
      return "";
    }
    const result = await redis.get(key);
    return result || "";
  } catch (err) {
    return "";
  }
};
