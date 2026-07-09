import Redis from "ioredis";
import {num10to62} from "../util/util";

var redis = new Redis();
// 自增序列key值
const AUTO_UID_KEY :string = 'auto_uid';
// 短连接的固定域名
const SHORT_DOMAIN :string = 'https://t.cn';

/***
 * 将参数的长域名转化为短域名，并保存到Redis中
 * @param longUrl
 * @return 
 */
const longToShort = async (longUrl: string): Promise<string>  => {
    
    if(longUrl === null || longUrl === undefined){
        return "";
    }
    
     //redis中查找该url的短url是否已经存在，存在则直接返回
    let shortUrl = "";
    await checkLongUrl(longUrl).then(function(value){
        shortUrl = value;
    });
    
    //redis中还不存在对应短url,则开始转换
    if(shortUrl == ""){
      let uid = "";
      //取得自增后的UID，
      await redis.get(AUTO_UID_KEY,function(err, result){
         uid = result;
    })
    // 将UID转化为62进制数作为短url
    shortUrl = num10to62(uid);
    if(shortUrl == "") {
        console.log("Error: Cannot get a number! ");
        return "";
    }

    shortUrl = SHORT_DOMAIN +"/" + shortUrl;
     // 将shorturl - longurl 存入Redis中
    await redis.set(shortUrl, longUrl,function(err, result) {
        if(err) {
            console.log("Redis set short url failed! "+ err);
            return "";
        }
        if(result) {
            console.log("Redis set short url succeed! "+ result);
        }
    });
    // 将redis中的UID增1
    await redis.incr(AUTO_UID_KEY);
   }
 async () => {
    await redis.quit()
  }
   
   return shortUrl;
}
/**
 * 查找该长url是否存在，若存在，则返回对应的短url
 * @param longUrl 
 * @returns 
 */
 async function checkLongUrl(longUrl:string):  Promise<string>{
    let shortUrl:string = "";

    //取得所有key
    const keys = await redis.keys('*', function(err, keys) {
    });

    //遍历所有value查找是否存在当前长url
    for(let i=0; i<keys.length; i++) {
        await redis.get(keys[i], function(err, result) {
            if(longUrl == result) {
                shortUrl = keys[i];
            }
        })
    }

    return shortUrl;
}

export {longToShort, redis};