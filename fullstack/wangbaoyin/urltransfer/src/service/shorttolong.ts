import Redis from "ioredis";

var redis = new Redis();

/***
 * 输入短域名, 做key查询,返回长域名
 * @param shortUrl
 * @return 
 */
 const shortToLong = async (shortUrl: string): Promise<string>  => {
    if(shortUrl === null || shortUrl === undefined){
        return "";
    }

    let longUrl = "";
    //取得所有key
    await redis.get(shortUrl, function(err, result) {
        longUrl = result;
    });
    async () => {
        await redis.quit()
    }
    return longUrl;
 }

 export  {shortToLong,redis};