import { MySQLStore } from '../store/mysql'
import { RedisCache } from '../cache/cache'
import {ToBase62Str, FromBase62Str } from '../utils/hash'

class URLSvc {
  private store: MySQLStore
  private cache: RedisCache
  constructor(store: MySQLStore, cache: RedisCache) {
    this.store = store
    this.cache = cache
    this.getURL = this.getURL.bind(this)
    this.saveURL = this.saveURL.bind(this)
  }

  async saveURL(longURL: string) {
    let short = await this.cache.get(longURL)
    if(short) {
      console.log('cache hit when get with key:' + longURL)
      return short
    }
    const id = await this.store.insertURL(longURL)
    short = ToBase62Str(id)
    this.cache.set(short, longURL).catch(err => {
      console.error('failed to set redis after insert url', err)
    })
    this.cache.set(longURL, short).catch(err => {
      console.error('failed to set redis after insert url', err)
    })
    return short
  }

  async getURL(shortURL: string): Promise<string> {
    let longURL = await await this.cache.get(shortURL)
    if (longURL) {
      console.log('cache hit when get with key:' + shortURL)
      return longURL
    }
    const id = FromBase62Str(shortURL)
    const res = await this.store.getURLByID(id)
    if (res) {
      longURL = res.long_url
      await this.cache.set(shortURL, longURL)
      return longURL
    }
    return ''
  }
}

export { URLSvc }