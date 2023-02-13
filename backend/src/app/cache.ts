import Redis from "ioredis";

const redisClient = new Redis(process.env.REDIS_URL || '', {
    maxRetriesPerRequest: 3,
});

redisClient.ping();


export default redisClient;