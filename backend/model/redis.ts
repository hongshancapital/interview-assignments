import Redis from "ioredis";

export const redis = new Redis({
    port: 'localhost',
    host: 6379,
    db: 0,
});