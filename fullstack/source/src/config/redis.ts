import { createClient } from 'redis';
import log4js from "log4js";

const logger = log4js.getLogger("app")

class Redis {
  private client
  constructor() {
    console.log("redis 初始化")
    this.client = createClient();
    this.client.on('error', err => console.log('Redis Client Error', err));
  }

  async setNx(key: string, value: any, second?: number): Promise<boolean> {
    await this.client.connect();

    try {
      let valueString = JSON.stringify(value)
      if (second) {
        let result = await this.client.setNX(key, valueString)
        await this.client.expire(key, second)
        return result
      } else {
        let result = await this.client.setNX(key, valueString)
        return result
      }
    } finally {
      this.client.quit()
    }
  }

  async get(key: string): Promise<any> {
    await this.client.connect();
    try {
      let data = await this.client.get(key)
      if (data) {
        this.client.quit()
        return JSON.parse(data)
      }
      this.client.quit()
      return null
    } finally {
      this.client.quit()
    }
  }

  async del(key: string) {
    await this.client.connect();
    await this.client.del(key)
    this.client.quit()
  }

}

export default new Redis()