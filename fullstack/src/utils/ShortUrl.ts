import IORedis from 'ioredis'
import { nanoid } from 'nanoid'
import { errors } from 'errorjs'

const generateId = () => nanoid(8)

export class ShortUrl {

  private redisClient: IORedis.Redis

  constructor(config: {
    host: string,
    port: number,
    password: string,
    keyPrefix: string,
  }) {
    this.redisClient = new IORedis({
      host: config.host,
      port: config.port,
      password: config.password,
      keyPrefix: config.keyPrefix,
    })
  }

  private async getAvailableId(retryTimes = 3) {
    while (retryTimes --> 0) {
      const id = generateId()
      const isExist = await this.redisClient.exists(id)
      if (!isExist) return id
    }
    throw new errors.ConflictError('can_not_get_available_id')
  }

  async create(url: string) {
    const id = await this.getAvailableId()
    await this.redisClient.set(id, url)
    return id
  }

  async get(id: string) {
    const result = await this.redisClient.get(id)
    if (!result) throw new errors.NotFoundError('id_not_found')

    return result
  }

}