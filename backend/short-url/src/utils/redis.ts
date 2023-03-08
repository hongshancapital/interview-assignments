import config from 'src/config';

import Redis from 'ioredis';
export const redis = new Redis(config.REDIS_URL);
