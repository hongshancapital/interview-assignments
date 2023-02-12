import redisClient from '../infrastructure/redis';
import crypto from 'crypto';

const originUrlPrefix:string = "http://"
const shortUrlPrefix:string = "http://";
const KeyPrefix:string = "short_url_hash_md5_key_";
const shortUrlKeyPrefix:string = "short_url_hash_key_";
const serialNumberKey:string = "short_url_serial_number";

const URL_SCHEME = /^https?\:\/\//i;

const digits:string[] = 
  [ '0', '1', '2', '3', '4', '5', '6', '7', '8',
  'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
  '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
  'Z', '-', '_'];

/**
 * 获取短域名地址
 * @param originUrl 原始域名
 * @returns 
 */
async function getShortUrl(originUrl: string) {
    originUrl = originUrl.replace(URL_SCHEME, "");
    let md5 = crypto.createHash('md5');
    const urlDigest:string = md5.update(originUrl).digest('hex');
    let shortUrl = await redisClient.get(KeyPrefix + urlDigest);
    if(null == shortUrl){
      const incrResult:number = await redisClient.incrBy(serialNumberKey, 1);
      const longString:string = compressNumber(incrResult);
      shortUrl = shortUrlPrefix + longString;
      await redisClient.set(KeyPrefix + urlDigest, shortUrl);
      await redisClient.set(shortUrlKeyPrefix + longString, originUrl);
    }
    
    return shortUrl;
  }

  /**
   * 获取原始域名地址
   * @param shortUrl 短域名地址
   * @returns 
   */
  async function getOriginUrl(shortUrl: string) {
    shortUrl = shortUrl.replace(URL_SCHEME, "");
    return originUrlPrefix + await redisClient.get(shortUrlKeyPrefix + shortUrl);
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
      result += digits[(num & mask)];
      num /= 64;
    } while (num >= 1);
  
    return result;
}


export default {
  getShortUrl,
  getOriginUrl
};
