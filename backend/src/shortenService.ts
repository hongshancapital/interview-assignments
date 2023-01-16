import { murmurHash32 } from 'murmurhash-native';

import { dataSource } from './orm'
import { UrlMap } from './entity/urlmap.entity';

export class ShortenService {
    /**
     * 检查原始链接是否已经生成过短链
     * @param originalUrl 原始链接
     * @return {boolean} true: 未生产过短链，false:已经生成过短链了
     */
    async checkLongUrl(originalUrl: string) : Promise<boolean> {
        const repository = dataSource.getRepository(UrlMap);
        const count = await repository.createQueryBuilder().where('long_url=:long_url', {long_url: originalUrl}).getCount();
        return count == 0;
    }
    
    /**
     * 检查短链是否重复了
     * @param url 短链
     * @returns {boolean} true: 短链没有重复， false：短链重复
     */
    async checkShortUrl(url: string): Promise<boolean> {
        const repository = dataSource.getRepository(UrlMap);
        const count = await repository.createQueryBuilder().where('short_url=:url', {url}).getCount();
        return count == 0;
    }
    
    /**
     * 通过短链获取到对应的原始链接
     * @param shortUrl 短链字符串
     * @returns 原始链接
     */
    async getOriginalUrl (shortUrl: string) : Promise<any> {
        const repository = dataSource.getRepository(UrlMap);
        const ret = await repository.createQueryBuilder().where('short_url=:short_url', {short_url:shortUrl}).getOne();
        if(ret != null) {
            return ret.long_url;
        }
        return '';
    }
    
    /**
     * hash生成短链
     * @param longUrl 原始链接
     * @returns 短链
     */
    async genShortUrl(longUrl: string) {
        let url = '';
        const originalUrl = longUrl;
        for(let i=0; i<3; i++) {
            // hash生成链接对应的哈希值
            const ret = murmurHash32(longUrl);
            // 将hash值转化成62进制[a-z][A-Z][0-9]
            url =  this.base62(ret);
            const checkRet = await this.checkShortUrl(url);
            if(checkRet) {
                // 短链正常可以存入数据库
                const repository = dataSource.getRepository(UrlMap);
                const urlMap = new UrlMap();
                urlMap.long_url = originalUrl;
                urlMap.short_url = url;
                await repository.save(urlMap);
                break;
            } else {
                // 短链重复了，修改下原始链接重新生成
                longUrl += '&'
            }
        }
        return url;
    }
    
    /**
     * 将10进制转化成62进制
     * @param hash 10进制hash值
     * @returns 
     */
    base62(hash: number): string {
        const charset = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
        let value = '';
        while (hash > 0) {
            const remainder = hash % 62;
            value += charset[remainder];
            hash = Math.round(hash/62)
        }
        return value;
    }
}
