import { ShortUrlGenerator } from "../utils/shorturlgenerator";
import { ShortUrlDao } from "../dao/shorturldao";

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
    private static shortUrlDao: ShortUrlDao  = new ShortUrlDao();
    private retryTimes: number;

    constructor() {
        this.retryTimes = SHORT_URL_INIT.RETRY_TIMES;
    }
    /**
     * 查询短链接
     */
    public queryOrGenerateShortUrl(src: string): string {
        let shortUrlID: string = ShortUrlService.shortUrlDao.queryShortUrl(src);
        if (shortUrlID != "") {
            return this.addDefaultDomain(shortUrlID);            
        }

        let shortUrl: string = "";
        shortUrlID = this.handleDuplicate(src + this.retryTimes.toString(), this.retryTimes);
        if (shortUrlID != "") {
            ShortUrlService.shortUrlDao.save(shortUrlID, src);
            shortUrl = this.addDefaultDomain(shortUrlID);            
        }
        return shortUrl;
    }

    /**
     * 查询长链接
     */
     public queryOriginalUrl(shortUrl: string): string {
         let shortUrlID: string = this.RemoveDefaultDomain(shortUrl);
        return ShortUrlService.shortUrlDao.queryOriginalUrl(shortUrlID);
     }

    /**
     * 生成短链接
     */ 
    private handleDuplicate(originalUrl: string,  retryTimes: number): string {

        let strurlArr: Array<string>;
        let strurl: string = "";
        
        if (retryTimes <= 0) {
            return "";
        }

        strurlArr = ShortUrlGenerator(originalUrl, SHORT_URL_INIT.SHORT_URL_LENGTH);
        if (strurlArr.length == 0) {
            return "";
        }

        for(let i: number = 0; i<strurlArr.length; i++) {
            let OriginalUrl:string = ShortUrlService.shortUrlDao.queryOriginalUrl(strurlArr[i]);
            if (OriginalUrl == "") {
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
        const  url_pre: string = "http://s.cn/";
        return url_pre+shortUrlID;        
    }      

    public RemoveDefaultDomain(shortUrl: string): string {
        return shortUrl.substring(shortUrl.lastIndexOf('/')+1);
    }
}