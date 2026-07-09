import { Request, Response, Router } from 'express';
import ShortUrlController from 'controllers/shortUrlController';
import * as CacheService from 'services/cacheService';
import ResCode from 'src/routes/resCode';
import isUrl from 'utils/isUrl';

const router = Router();

router.post('/', async (req: Request, res: Response) => {
  try {
    const { longUrl, length = 8 } = req.body;

    if (!isUrl(longUrl)) {
      return res.json({ data: null, code: ResCode.InvalidData });
    }

    const doc = await ShortUrlController.create(longUrl, length);
    /* istanbul ignore else */
    if (doc?.shortUrl) {
      CacheService.set(doc.shortUrl, doc);
      return res.json({ data: doc });
    }
  } catch (error) {
    return res.json({ data: null, error });
  }

  return res.json({ data: null });
});

router.get('/', async (req: Request, res: Response) => {
  try {
    const { url } = req.query;

    // check cache
    const cacheData = CacheService.get(url as string);
    /* istanbul ignore else */
    if (cacheData) {
      return res.json({ data: cacheData });
    }

    /* istanbul ignore else */
    if (url) {
      const doc = await ShortUrlController.findByShortUrl(url as string);
      if (doc) {
        return res.json({ data: doc });
      }
    }
  } catch (error) {
    return res.json({ data: null, error });
  }

  return res.json({ data: null, code: ResCode.NotExist });
});

export default router;
