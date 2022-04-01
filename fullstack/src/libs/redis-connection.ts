import 'dotenv/config';
import Redis from 'ioredis';
import { EventEmitter } from 'stream';

const redis = new Redis({
  port: Number(process.env.REDIS_PORT),
  host: process.env.REDIS_HOST,
  enableReadyCheck: true,
});

const emitter = new EventEmitter();
async function onReady() {
  console.log('redis connected successfully');
}
redis.on('ready', onReady);

redis.on('error', (err) => {
  console.error('redis connection failed', err);
  process.exit(1);
});

export { redis };
