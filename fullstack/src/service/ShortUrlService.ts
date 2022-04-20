
import { ShortUrlMap } from '../entity/ShortUrlMap';
import { AppDataSource } from "../data-source"
import { ShortUrlUtils } from '../utils/shortUrlUtils';

export class ShortUrlService{
    private shortUrlUtils: ShortUrlUtils = new ShortUrlUtils();
    // 判断长链接是否在数据库中
    public async isLongUrlExist(longUrl: string): Promise<boolean>{
        return await AppDataSource.manager.findOne(ShortUrlMap, {where:{
            longUrl: longUrl
        }}) != null;
    }

    // 判断短链接是否在数据库中
    public async isShortUrlExist(shortUrl: string): Promise<boolean>{
        return await AppDataSource.manager.findOne(ShortUrlMap, {where:{
            shortUrl
        }}) != null;
    }

    // 根据短链接获取长链接
    public async getLongUrlByShortUrl(shortUrl: string): Promise<string>{
        const shortUrlMap = await AppDataSource.manager.findOne(ShortUrlMap, {where:{
            shortUrl
        }});
        if(shortUrlMap){
            return shortUrlMap.longUrl;
        }
        return null;
    }

    // 根据长链接获取短链接
    public async getShortUrlByLongUrl(longUrl: string): Promise<string>{
        const shortUrlMap = await AppDataSource.manager.findOne(ShortUrlMap, {where:{
            longUrl
        }});
        if(shortUrlMap){
            return shortUrlMap.shortUrl;
        }
        return null;
    }

    // 根据长链接获取链接实体
    public async getShortUrlMapByLongUrl(longUrl: string): Promise<ShortUrlMap>{
        return await AppDataSource.manager.findOne(ShortUrlMap, {where:{
            longUrl
        }});
    }
  



    // 根据长链接生成短链接hash
    public async generateShortUrlHash(longUrl: string,count: number = 0): Promise<string>{
        if(count>3){
            throw new Error("生成短链接失败");
        }        
        const shortUrl = this.shortUrlUtils.generateShortHash(longUrl);
        // 处理冲突
        if(!await this.isShortUrlExist(shortUrl)){
            return shortUrl;
        }
        // 生成短链接失败，重新生成,超过三次直接报错
        return this.generateShortUrlHash(longUrl,count+1);
    }

    // 保存短链接并返回短链接实体
    public async saveShortUrl(shortHash: string,longUrl: string): Promise<ShortUrlMap>{
        const shortUrlMap = new ShortUrlMap();
        shortUrlMap.shortUrl = this.shortUrlUtils.generateShortUrl(longUrl,shortHash);
        shortUrlMap.longUrl = longUrl;
        if(!shortUrlMap.shortUrl){
            throw new Error("生成短链接失败");
        }
        return await AppDataSource.manager.save(shortUrlMap);
    }
}