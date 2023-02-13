/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:37:40
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 19:19:06
 * @FilePath: /interview-assignments/fullstack/src/modules/short-url/controller.ts
 */
/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:37:40
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 17:31:55
 * @FilePath: /interview-assignments/fullstack/src/modules/short-url/controller.ts
 */
import { Request, Response, Router } from 'express';
import { createShortUrlService, getShortUrlService } from './service';

const router  = Router();


router.post('/short-urls', async (req: Request, res: Response) => {
  const url = req.body.url;
  const shortUrl = await createShortUrlService(url);
  res.send(shortUrl);
});

router.get('/:shortId', async (req: Request, res: Response) => {
  const shortId = req.params.shortId;
  const shortUrl = await getShortUrlService(shortId);
  res.redirect(shortUrl.url);
});

export default router;
