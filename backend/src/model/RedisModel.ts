import { Redis } from "ioredis";
import config from "../config/config";

const RedisClient = new Redis(config.REDIS);

export default RedisClient;