import * as redis from 'redis'
import logger from './logger';
import util from './util';
import globalConfig from '../config';

interface SubscribeRes {
  unsubscrbe (): void;
}
class ReidsUtil {
  client: redis.RedisClient
  onClose: () => void
  constructor(client: redis.RedisClient, onClose: () => void) {
    this.client = client
    this.onClose = onClose
  }
  close(): void {
    // this.client.end(true)
    // this.client.quit()
    this.onClose()
  }
  hgetall (key: string): Promise<object> {
    return new Promise((resolve, reject) => {
      this.client.hgetall(key, (err: Error, data: any) => {
        if (err) {
          logger.error('redis hgetall error', key)
          reject(err)
        } else {
          logger.info('redis hgetall success: ', key)
          resolve(data)
        }
      })
    })
  }
  hget (key: string, field: string): Promise<string> {
    return new Promise((resolve, reject) => {
      this.client.hget(key, field, (err, res) => {
        if (err) {
          reject(err)
        } else {
          logger.info(`redis hget: ${key}>>>${field}`)
          resolve(res)
        }
      })
    })
  }
  zrange (key: string, start: number, stop: number): Promise<string[]> {
    return new Promise((resolve, reject) => {
      this.client.zrange(key, start, stop, (err: Error, data: string[]) => {
        if (err) {
          logger.error('redis zrange error', err)
          reject(err)
        } else {
          logger.info('redis zrange success', key)
          resolve(data)
        }
      })
    })
  }
  subscribe (channel: string, cb: (msg: string) => void): SubscribeRes {
    logger.info('订阅事件:' + channel, this.client.info)
    const client = this.client
    client.subscribe(channel)
    client.on('message', (channel: string, message: string) => {
      cb(message)
    })
    return {
      unsubscrbe (): void {
        logger.info('取消订阅')
        client.unsubscribe(channel)
        client.end(true)
      }
    }
  }
  hset (key: string, field: string, value: string, expire?: number): Promise<number> {
    return new Promise((resolve, reject) => {
      this.client.hset(key, field, value, (err: Error, reply: number) => {
        if (err) {
          reject(err)
        } else {
          resolve(reply)
        }
      })
      this.client.expire(key, expire || 86400) // 默认存储一天的时间
    })
  }
  del (key: string | string[]): void {
    this.client.del(key)
  }
}
const poolMap: object = {}
class RedisPool {
  poolSize = globalConfig.redis.pooSize
  maxPoolSize = globalConfig.redis.maxPoolSize
  initPool (ip: string, port: number, password?: string): void {
    logger.info('初始化redis连接池', `${ip}:${port}`)
    for (let i = 0; i < this.poolSize; i++) {
      this.createClient(ip, port, password)
    }
  }
  createClient (ip: string, port: number, password?: string): redis.RedisClient {
    let client: redis.RedisClient = null
    const config: redis.ClientOpts = {
      host: ip,
      port: port
    }
    if (password) {
      config.password = password
    }
    client = redis.createClient(config)
    logger.info('创建redis连接实例', ip, port)
    client.on('error', (error: Error) => {
      logger.error('redis错误', error)
    })
    poolMap[`${ip}:${port}`].list.push(client)
    poolMap[`${ip}:${port}`].status[client.connection_id] = 'rest'
    return client
  }
  async getClient (param: {
    ip: string; 
    port: number;
    password?: string;
  }): Promise<ReidsUtil> {
    const ip = param.ip
    const port = param.port
    const key = `${ip}:${port}`
    if (!poolMap[key]) {
      poolMap[key] = {
        list: [],
        status: {}
      }
      this.initPool(ip, port, param.password)
    }
    let client: redis.RedisClient = null
    // 从连接池中取空闲连接
    for (let i = 0; i < poolMap[key].list.length; i++) {
      const item: redis.RedisClient = poolMap[key].list[i]
      if (poolMap[key].status[item.connection_id] === 'rest') {
        client = item
      }
    }
    // 如果没有取到有效连接，并且连接池的数量小于最大值，则新建一条连接
    if (!client && poolMap[key].list.length < this.maxPoolSize) {
      client = this.createClient(ip, port)
    }
    if (client) {
      poolMap[key].status[client.connection_id] = 'busy'
      return new ReidsUtil(client, () => {
        // eslint-disable-next-line no-debugger
        // debugger
        poolMap[key].status[client.connection_id] = 'rest'
      })
    } else { // 等待空闲连接出来
      await util.delay(300)
      const res = await this.getClient({
        ip,
        port
      })
      logger.info('redis等待成功，拿到空闲连接')
      return res
    }
  }
}

const redisClient = new RedisPool()
export default redisClient