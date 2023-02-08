import express from 'express';
import asyncHandler from 'express-async-handler';

import { errorType } from '@/configs';
import { check, isURL } from '@/utils';
import { Domains } from '@/dal';

const router = express.Router();

/**
 * @api {get} /api/v1/domain/original 根据短域名获取原始域名
 * @apiGroup 域名映射
 * @apiVersion 1.0.0
 * @apiParam (query) {string} shortUrl 短域名
 * @apiSuccessExample Success-Response
 *       {
 *           "url": "http://xxx.xx/xxx/xx/..."
 *       }
 */
router.get('/original', asyncHandler(async (req, res) => {
    check(req.query, {
        shortUrl: { type: String, notEmpty: true, required: true }
    });
    const { shortUrl } = req.query as { shortUrl: string };

    if (!isURL(shortUrl)) {
        throw new Exception('invalid short url.', errorType.INVALID_ARGUMENTS);
    }
    if (new URL(shortUrl).pathname.length > 8) {
        throw new Exception('invalid short url.', errorType.FORBIDDEN);
    }
    const data = await Domains.getOriginalDomain(shortUrl);

    if (!data) {
        throw new Exception(`can not find original domain by ${shortUrl}.`);
    }
    res.success({ url: data });
}));

export default router;
