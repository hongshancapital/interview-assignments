import { createClient, SchemaFieldTypes } from 'redis'

export async function initConnection() {
  const url = process.env.REDIS_URL
  const client = createClient({
    url: url
  })

  client.on('error', err => console.error('[redis]', err));

  console.log(`[redis] connecting ${url}`)
  await client.connect()
  console.log('[redis] connected')


  // 创建索引
  await client.ft.CREATE('idx:UrlStoreData', {
    '$.short': {
      type: SchemaFieldTypes.TEXT,
      AS: 'short'
    },
    '$.url': {
      type: SchemaFieldTypes.TEXT,
      AS: 'url'
    }
  })

  return client
}

