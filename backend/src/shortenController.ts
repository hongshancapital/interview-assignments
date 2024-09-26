import { Request, Response, NextFunction } from 'express'
import { validationResult } from 'express-validator';

import { ShortenService } from './shortenService';

export class ShortenController {
    /**
     * 获取短链接对应的原始链接
     * @param req 
     * @param res 
     * @param next 
     * @returns 
     */
    async getOriginalUrl(req: Request, res: Response, next: NextFunction) {
        if(!validationResult(req).isEmpty()){
            res.status(400).end();
            return;
        }
        const shortenService = new ShortenService();
        const originalUrl = await shortenService.getOriginalUrl(req.params['url']);
        res.send({
            data: { originalUrl },
            suc: true,
            msg: '',
        })
    }
    
    /**
     * 生成短链接
     * @param req 
     * @param res 
     * @param next 
     * @returns 
     */
    async genShortUrl(req: Request, res: Response, next: NextFunction) {
        const {originalUrl} = req.body;
        const shortenService = new ShortenService();
        // 检查longUrl 是否已经生成过
        const check = await shortenService.checkLongUrl(originalUrl);
        if(!check) {
            res.send({
                data: {},
                suc: false,
                msg: '短链已经生成过了，不用重复生成',
            });
            return;
        }
        const url: string = await shortenService.genShortUrl(originalUrl);
        res.send({
            data: {
                shortUrl: url,
            },
            suc: true,
            msg: '',
        })
    }
}

