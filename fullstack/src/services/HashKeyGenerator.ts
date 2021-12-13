import { nanoid } from 'nanoid'
import * as bloomFilter from '../components/bloomFilter'

export interface KeyGenerator {
  generateKey(keyLength: number): Promise<string>
}
interface Config {
  shortUrlFilter: string
}
export default class implements KeyGenerator {
  constructor(public config: Config) {}
  generateKey = async (keyLength: number) => {
    let shortUrlKey = await nanoid(keyLength)
    while (await bloomFilter.exists(this.config.shortUrlFilter, shortUrlKey)) {
      shortUrlKey = nanoid(keyLength)
    }
    return shortUrlKey
  }
}
