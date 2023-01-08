import Redis from 'ioredis'
import { RedisConfig } from '../types/type'

class RedisCache {
  private instance: Redis
  private bloomFilter: any

  constructor(config: RedisConfig) {
    this.instance = new Redis(config)
    this.set = this.set.bind(this)
    this.get = this.get.bind(this)
  }

  async set(key: string, value: string) {
    try {
      await this.instance.set(key, value)
    } catch (err) {
      console.error(`failed to set ${key}: ${value}, err:`, err)
      throw new Error(`failed to set ${key}: ${value}`)
    }
  }

  async get(key: string): Promise<string> {
    let val: string
    try {
      val = (await this.instance.get(key)) as string
    } catch (err) {
      console.error(`failed to get ${key}, err:`, err)
      throw new Error(`failed to get ${key}`)
    }
    return val
  }
}

export { RedisCache }