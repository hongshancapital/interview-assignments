import { createClient, SchemaFieldTypes } from 'redis'
import { RedisMemoryServer } from 'redis-memory-server'

export async function initRedisConnnection() {
  // 这里是作业先用 RedisMemoryServer 代替，这样省的装redis
  const redisServer = new RedisMemoryServer();

  const host = await redisServer.getHost();
  const port = await redisServer.getPort();

  const url = `redis://${host}:${port}`
  const client = createClient({
    url
  })

  client.on('error', err => console.error('[redis]', err));

  console.log(`[redis] connecting ${url}`)
  await client.connect()
  console.log('[redis] connected')

  return client
}

