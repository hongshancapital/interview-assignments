import redis, { initializeRedis } from './redis'
export async function add(filter: string, value: string) {
  return await redis.sendCommand<number>(['BF.ADD', filter, value])
}
export async function exists(filter: string, value: string) {
  const ret = await redis.sendCommand<number>(['BF.EXISTS', filter, value])
  return ret == 1
}

export async function initializeBloomFilter() {
  initializeRedis()
}

export async function reserve(
  filter: string,
  errorRate: number,
  capacity: number,
) {
  const ret = await redis.sendCommand<number>([
    'BF.EXISTS',
    filter,
    errorRate.toString(),
    capacity.toString(),
  ])
  return ret == 1
}
