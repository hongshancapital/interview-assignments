import {UrlDao} from "./dao";
import * as Util from '../util';
import Config from '../config/config';
import {get as getCache, set as setCache} from "../db/redis";

const urlDao = new UrlDao();

export class UrlService {
    constructor() {
    }

    /**
     * 短链接存储
     * @param longUrl 
     * @returns 
     */
    async putUrl(longUrl: string): Promise<string> {
        if (!longUrl) {
            Util.throwErr('406_missing_param', '缺少必有参数[longUrl]', 'ER230217141752');
        }
        if (!Config || !Config.shortHost) {
            Util.throwErr('500_config_err', '配置文件错误[缺少必有值][shortHost]', 'ER230217142553');
        }
        Util.checkURL(longUrl);

        // check是否已有短链
        const short = await urlDao.getId(longUrl);
        if (short) {
            return this.cvtIdToShortUrl(short);
        }

        // 入库
        const urlRecord = await urlDao.creatUrl(longUrl);

        return this.cvtIdToShortUrl(urlRecord.id);
    };

    /**
     * 短链接读取
     * @param shortUrl 
     * @returns 
     */
    async getLongUrl(shortUrl: string): Promise<string> {
        if (!shortUrl) {
            Util.throwErr('406_missing_param', '缺少必有参数[shortUrl]', 'ER230217163112');
        }
        // check url format
        Util.checkShortUrl(shortUrl);
        // 摘取path; check format
        let seg = /https?:\/\/.*?\/([^]+)/i.exec(shortUrl);
        const shortPath = seg && seg[1];
        if (!shortPath || !Util.isShortPathValid(shortPath)) {
            Util.throwErr('406_unknown_url', `无法识别的短链接[shortUrl=${shortUrl}]`, 'ER230217163226');
        }
        // 查cache
        let longUrl: string = await getCache(shortPath);
        if (longUrl) {
            return longUrl;
        }
        // 转10进制，查db
        const id = Util.decodeTo10(shortPath);
        longUrl = await urlDao.getLongUrl(id);
        if (!longUrl) {
            Util.throwErr('404_not_hit', `短链接不存在[shortUrl=${shortUrl}]`, 'ER230217172008')
        }
        // 入cache，异步即可
        setCache(shortPath, longUrl);

        return longUrl;
    };

    /**
     * 十进制 id 转换为短链接
     * @param id 
     * @returns 
     */
    cvtIdToShortUrl(id: number): string {
        // id转62进制
        const shortPath = Util.encodeToBase62(id);
        // 拼接域名返回
        return `${Config.shortHost}/${shortPath}`;
    };
}
