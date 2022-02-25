import { ShortUrlGenerator } from "../utils/shorturlgenerator";
import { ShortUrlDao } from "../db/shorturldao";
import { redisCache } from "../cache/redis"
import cfgs from "../config/config";

export enum SHORT_URL_INIT {
    RETRY_TIMES = 5,                // 成功
    SHORT_URL_LENGTH = 8,  // 请求参数异常
}


/**
 * / route
 *
 * @class ShortUrlService
 */
export class ShortUrlService  {
    
    // 设置为静态全局变量，用于数据保存
    private shortUrlDao: ShortUrlDao;
    private retryTimes: number;

    constructor() {
        this.retryTimes = SHORT_URL_INIT.RETRY_TIMES;
        this.shortUrlDao = new ShortUrlDao();
    }
    /**
     * 查询短链接
     */
    public async queryOrGenerateShortUrl(srcLongUrl: string)  {

        //在缓存查找是否已经生成过了
        let shortUrlID  = await redisCache.GetVal(srcLongUrl);
        if (shortUrlID != null) {
            return this.addDefaultDomain(shortUrlID as string); 
        }

        shortUrlID  = await this.handleDuplicate(srcLongUrl + this.retryTimes.toString(), this.retryTimes)

        // 重新生成ID失败
        if (shortUrlID == "") {
            return null;
        }

        // 新增到数据库和布隆过滤器
        let result = await this.shortUrlDao.create({"shorturlid":shortUrlID, "longurl":srcLongUrl, "createdata":(new Date()).toLocaleDateString()});
        if (result) {
            return null;
        }
        await redisCache.BfAdd(shortUrlID);
        await redisCache.SetVal(srcLongUrl, shortUrlID);

        return this.addDefaultDomain(shortUrlID);
    }

    /**
     * 查询长链接
     */
     public async queryOriginalUrl(strShortUrl: string) {
         let shortUrl = await this.shortUrlDao.getByShortUrlid(this.RemoveDefaultDomain(strShortUrl))
         if (shortUrl == null)
            return null;
        return shortUrl.longurl;
     }

    /**
     * 生成短链接
     */ 
    private async handleDuplicate(originalUrl: string,  retryTimes: number): Promise<string> {
        if (retryTimes <= 0) {
            return "";
        }

        let strurl: string = "";
        let strurlArr: Array<string> = ShortUrlGenerator(originalUrl, SHORT_URL_INIT.SHORT_URL_LENGTH);
        for(let i: number = 0; i<strurlArr.length; i++) {
            let idExists = await redisCache.BfExists(strurlArr[i]);
            if (!idExists) {
                strurl = strurlArr[i];
                break;
            }    
        }

        // 没有获取到有效的短链接，就继续尝试生成，直到机会次数用完
        if (strurl == "") {
            retryTimes = retryTimes -1;
            return this.handleDuplicate(originalUrl+retryTimes.toString(), retryTimes);
        } 
        return strurl;
    }

    /**
     * 完成链接的域名转换
     */
    public addDefaultDomain(shortUrlID: string): string {
        return cfgs.shorturl_pre+shortUrlID;        
    }      

    public RemoveDefaultDomain(shortUrl: string): string {
        return shortUrl.substring(shortUrl.lastIndexOf('/')+1);
    }
}