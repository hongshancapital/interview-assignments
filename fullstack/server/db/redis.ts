// 未实现

import { createClient,RedisClientOptions } from 'redis';

const client = createClient({
  url: 'localhost:6379'
});

client.connect()

client.on('error', (err) => console.log('Redis Client Error', err));

