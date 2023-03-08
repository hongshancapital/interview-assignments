import { Request, Response, Router } from 'express';
import ShortUrlController from 'src/controllers/short-url';
import { ResponseCode, isUrl } from 'src/utils';

const router = Router();

router.post('/', async (req: Request, res: Response) => {
    try {
        const { longUrl, expiredAt } = req.body;

        if (!isUrl(longUrl)) {
            return res.json({ data: null, code: ResponseCode.InvalidData });
        }

        const doc = await ShortUrlController.create(longUrl, expiredAt);
        return res.json({ data: doc });
    } catch (error) {
        return res.json({ data: null, error });
    }
});

router.get('/:shortUrl', async (req: Request, res: Response) => {
    try {
        const { shortUrl } = req.params;

        if (shortUrl) {
            const doc = await ShortUrlController.findByShortUrl(shortUrl);
            if (doc) {
                return res.json({ data: doc });
            }
        }
    } catch (error) {
        return res.json({ data: null, error });
    }

    return res.json({ data: null, code: ResponseCode.NotExist });
});

export default router;
