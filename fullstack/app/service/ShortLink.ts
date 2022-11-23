import { Service } from 'egg';

/**
 * home Service
 */
export default class ShortLink extends Service {
  public keyPrefix = 'short_link_key_';
  public originUrlPrefix = 'short_link_url_';
  public async getRandomKey() {
    const str = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const id = new Array(8).fill('').map(() => str[Math.floor(Math.random() * str.length)])
      .join('');
    const isHas = await this.app.redis.get(this.keyPrefix + id);
    return isHas ? this.getRandomKey() : id;
  }

  public checkUrl(url: string) {
    try {
      new URL(url);
    } catch (error) {
      return false;
    }
    return true;
  }
}
