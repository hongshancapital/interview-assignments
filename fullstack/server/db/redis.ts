// 未实现

import { createClient } from 'redis';

const client = createClient({
  url: 'redis://localhost:6379',
});

client.connect()

export const setValue = async (key: string, value: string) => {
  return await client.set(key, value);
}


export const getValue:(key: string) => Promise<string | null> = async (key: string) => {
  const value = await client.get(key);
  return value;
}

client.on('error', (err) => console.log('Redis Client Error', err));

