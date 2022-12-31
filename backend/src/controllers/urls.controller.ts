import { NextFunction, Request, Response } from 'express';
import { HttpException } from '@exceptions/HttpException';
import { ShortUrl } from '@/interfaces/urls.interface';
import UrlService from '@/services/urls.service';

class UrlController {
  public urlService = new UrlService();

  public getUrls = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
    try {
      const findAllUrlsData: ShortUrl[] = await this.urlService.findAllUrl();
      res.status(200).json({ data: findAllUrlsData, message: 'ok' });
    } catch (error) {
      next(error);
    }
  };

  public getByShortUrl = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
    try {
      const url: string = req.params.url;
      if (!url || url.length != 8) throw new HttpException(400, "Url params invalid");
      const data: ShortUrl = await this.urlService.findByShortUrl(url);
      res.status(200).json({ data: data.long, message: 'ok' });
    } catch (error) {
      next(error);
    }
  };

  public createWithLongUrl = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
    try {
      const { url }  = req.body;
      if (!url || url.length > 1024) throw new HttpException(400, "Url params invalid");
      const data: ShortUrl = await this.urlService.createWithLongUrl(url);
      res.status(201).json({ data: data, message: 'ok' });
    } catch (error) {
      next(error);
    }
  };
}

export default UrlController;
