import {Request, Response} from 'express';

import * as service from '../services/shortener_service';

export function getLongUrl (req: Request, res: Response): Response {
    const shortUrl = req?.params?.shortUrl
    if (shortUrl == null 
        || shortUrl.length !== service.SHORT_URL_LENGTH) {
        return res.status(400).json({data: 'Invalid short URL'});
    }
    try {
        const longUrl =  service.getLongUrl(req.url);
        return res.json({data: longUrl});
    } catch (e) {
        return res.status(400).json({
            data: 'Invalid short URL', 
            error: (<Error>e).message,
        });
    }
}

export function generateShortUrl (req: Request, res: Response): Response {
    const longUrl = req?.body?.url;
    if (longUrl != null) {
        try {
            const url = new URL(longUrl);
        } catch (_) {
            return res.status(400).json({data: 'Invalid URL'});
        }
        return res.json({data: service.shortenUrl(longUrl)});
    } else {
        return res.status(400).json({data: 'Invalid URL'});
    }
}
