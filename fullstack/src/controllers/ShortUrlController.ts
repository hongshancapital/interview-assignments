import { Request, Response } from 'express';
import ShortUrlService from '../services/ShortUrlService';


import ShortUrl, { IUrl } from '../models/ShortUrlModel';

class ShortUrlController {
    getUrlbyShortId = async (req: Request, res: Response) => {
        const { shortId } = req.params;
        if (!shortId) {
            return res.status(400).json({ message: 'shortId is not provided' });
        }
        try {
            const record: IUrl | null = await ShortUrl.findOne({ shortId });
            if (!record) {
                return res.status(400).json({ message: 'shortId is invalid' });
            }
            return res.status(200).json({ url: record.url, shortUrl: record.shortUrl});
        } catch (error) {
            return res.status(500).json({ message: 'Some thing went wrong!' });
        }
    }  
    shortenUrl = async (req: Request, res: Response) => {
        const { url } = req.body;
        if (!url) {
            return res.status(400).json({ message: 'url is not provided' });
        }
        if (!ShortUrlService.validateUrl(url)) {
            return res.status(400).json({ message: 'url is invalid' });
        }
        try {
            const record: IUrl | null = await ShortUrl.findOne({ url });
            if (record) {
                return res.status(200).json({ url: record.url, shortUrl: record.shortUrl });
            }

            const seqNumber: number = await ShortUrl.countDocuments();
            const shortId = ShortUrlService.decimalTo62(seqNumber);
            if (shortId.length > 8) {
                return res.status(500).json({ message: 'We have ran out of short IDs' });
            }

            const formattedUrl = new URL(url);
            const shortUrl = formattedUrl.origin + '/' + shortId;
            const newUrl = {
                url,
                shortId,
                shortUrl
            };
            const newRecord: IUrl | null = await ShortUrl.create(newUrl);
            return res.status(200).json({ url: newRecord.url, shortUrl: newRecord.shortUrl });
        } catch (error) {
            return res.status(500).json({ message: 'Some thing went wrong!' });
        }
    }
}

export default new ShortUrlController();
