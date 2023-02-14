import redisClient from '../infrastructure/redis';
import db from '../dao/shortUrlDao';

const originUrlPrefix:string = "http://"
const shortUrlPrefix:string = "http://";
const serialNumberKey:string = "short_url_serial_number";

const URL_SCHEME = /^https?\:\/\//i;
const digits:string = '012345678zABCDEFGHIJKL9abcdefghijklmnopqrstuvwxyMNOPQRSTUVWXYZ-_';

/**
 * 获取短域名地址
 * @param originUrl 原始域名
 * @returns 
 */
async function getShortUrl(originUrl: string): Promise<string> {
    originUrl = originUrl.replace(URL_SCHEME, "");
    let shortUrl:string = await db.queryShortUrl(originUrl);
    if(null == shortUrl || "" == shortUrl){
      const incrResult:number = await redisClient.incrBy(serialNumberKey, 1);
      const address:string = compressNumber(incrResult);
      await db.saveShortUrl(address, originUrl);
      shortUrl = address;
    }
    
    return shortUrlPrefix + shortUrl;
  }

  /**
   * 获取原始域名地址
   * @param shortUrl 短域名地址
   * @returns 
   */
  async function getOriginUrl(shortUrl: string) {
    shortUrl = shortUrl.replace(URL_SCHEME, "");
    const originUrl:string = await db.queryOriginUrl(shortUrl);
    return null != originUrl ? originUrlPrefix + await db.queryOriginUrl(shortUrl) : "";
  }

/**
 * 将redis的自增值压缩64倍
 * @param num redis的自增值
 * @returns 
 */
function compressNumber(num:number):string {
    let result:string = "";
    const mask:number = 64 - 1;
    do {
      if(result.length == 8){
        throw new Error("The length of the short url exceeds the maximum length: 8");
      }
      result += digits.charAt(num & mask);
      num /= 64;
    } while (num >= 1);
  
    return result;
}


export default {
  getShortUrl,
  getOriginUrl
};
