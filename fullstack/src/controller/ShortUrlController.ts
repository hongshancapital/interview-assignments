import { NextFunction, Request, Response } from "express"
import { ShortUrlMap } from "../entity/ShortUrlMap"
import { ShortUrlService } from "../service/shortUrlService"

export class ShortUrlController {


    constructor(
        // 这里考虑使用依赖注入
        private shortUrlService: ShortUrlService = new ShortUrlService(),
    ) {
        
    }

    // 根据长链接获取短链接
    public async getShortUrl(req: Request, res: Response, next: NextFunction) {
        const longUrl = req.query.longUrl;
        if(!longUrl){
            res.status(400).send("缺少参数");
            return;
        }
        // 判断长链接是否存在短链接
        const exitsShortUrl = await this.shortUrlService.getShortUrlMapByLongUrl(longUrl);
        if(exitsShortUrl){
            res.send({
                shortUrl:exitsShortUrl.shortUrl,
                longUrl,
                message:'success'
            });
            return 
        }
        // 生成短链接
        const shortHash = await this.shortUrlService.generateShortUrlHash(longUrl);
        // 保存短链接
        const shortUrl = await this.shortUrlService.saveShortUrl(shortHash,longUrl);
        if(shortUrl){
            res.send({
                shortUrl,
                longUrl,
                message:'success'
            });
        }else{
            res.status(400).send({error:'',message:"获取短链接失败"});
        }
    }

    // 根据短链接获取长链接
    public async getLongUrl(req: Request, res: Response, next: NextFunction) {
        const shortUrl = req.query.shortUrl;
        if(!shortUrl){
            res.status(400).send({error:'',message:"缺少参数"});
            return;
        }
        // 判断短链接是否存在长链接
        const exitsLongUrl = await this.shortUrlService.getLongUrlByShortUrl(shortUrl);
       
        // 生成短链接
        if(exitsLongUrl){
            res.send({
                shortUrl,
                longUrl:exitsLongUrl,
                message:'success'
            });
        }else{
            res.status(400).send({error:'',message:"获取短链接失败"});
        }
    }



}