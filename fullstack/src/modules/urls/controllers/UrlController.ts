import { Request, Response } from 'express';
import { container } from 'tsyringe';

import ShortenUrlService from '@modules/urls/services/ShortenUrlService';

class UrlController {

  public async createShortUrl(request: Request, response: Response): Promise<Response> {
    const { url } = request.body;

    const shortenUrlService = container.resolve(ShortenUrlService);
    const shortUrl = await shortenUrlService.create(url);

    return response.status(200).json({
      shortUrl
     });
  }

  public async getOriginUrl(request: Request, response: Response): Promise<Response> {
    const { shortUrl } = request.query;

    const shortenUrlService = container.resolve(ShortenUrlService);
    const originUrl = await shortenUrlService.find(shortUrl as string);

    return response.status(200).json({
      originUrl
    });
  }
}

export default UrlController;
