import { ShortUrlGenerator } from "../utils/shorturlgenerator";
import { ShortUrlDao } from "../db/shorturldao";
import { redisCache } from "../cache/redis"
import cfgs from "../config/config";

/**
 * 
 *
 * @class ShortUrlService
 */
class ShortUrlService  {
    
    private shortUrlDao: ShortUrlDao;
    constructor() {
        
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

        // handleDuplicate通过布隆过滤器来判定是否重复生成
        shortUrlID  = await this.handleDuplicate(srcLongUrl + cfgs.retrynum.toString(), cfgs.retrynum)
        if (shortUrlID == "") {
            return null;
        }

        // 新增到数据库、布隆过滤器、缓存中
        let result = await this.shortUrlDao.create({"shorturlid":shortUrlID, "longurl":srcLongUrl, "createdata":(new Date()).toLocaleDateString()});
        if (result == null) {
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

        // 短链接ＩＤ
        let strShortUrlID = this.RemoveDefaultDomain(strShortUrl)

        // 先查询缓存
        let strLongUrl = await redisCache.GetVal(strShortUrl);
        if (strLongUrl != null) {
            return strLongUrl;
        }

        //查询布隆过滤器
        let bfExists: Boolean = await redisCache.BfExists(strShortUrlID);
        if (!bfExists) {
            return null;
        }

        // 查询数据库
        let shortUrl = await this.shortUrlDao.getByShortUrlid(strShortUrlID)
         if (shortUrl == null) {
            return null;
         }

        // 添加缓存
        await redisCache.SetVal(strShortUrl, shortUrl.longurl); 

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
        let strurlArr: Array<string> = ShortUrlGenerator(originalUrl, cfgs.urllen);
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

    
    //完成链接的域名转换 
    public addDefaultDomain(shortUrlID: string): string {
        return cfgs.shorturl_pre+shortUrlID;        
    }      

    public RemoveDefaultDomain(shortUrl: string): string {
        return shortUrl.substring(shortUrl.lastIndexOf('/')+1);
    }
}

export default new ShortUrlService();