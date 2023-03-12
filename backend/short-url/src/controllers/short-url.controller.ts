import { NextFunction, Request, Response } from 'express';
import { CreateShortUrlDto } from '@dtos/short-url.dto';
import ShortUrlService from '@services/short-url.service';

class ShortUrlController {
    public shortUrlService = new ShortUrlService();

    public getUrl = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
        try {
            const shortUrl = req.params.short;
            const shortUrlData = await this.shortUrlService.findByShortUrl(shortUrl);
            res.status(200).json({ data: shortUrlData, message: 'ok' });
        } catch (error) {
            next(error);
        }
    };

    public create = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
        try {
            const { longUrl, expiredAt }: CreateShortUrlDto = req.body;

            const doc = await this.shortUrlService.createShortUrl(longUrl, expiredAt);
            res.status(201).json({ data: doc, message: 'ok' });
        } catch (error) {
            next(error);
        }
    };
}

export default ShortUrlController;
