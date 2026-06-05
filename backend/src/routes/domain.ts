import express from 'express';
import asyncHandler from 'express-async-handler';
import { DomainService } from '@/services';

const router = express.Router();

router.route('/domain')
    /**
     * @api {post} /api/v1/domain 新增
     * @apiGroup 域名映射
     * @apiVersion 1.0.0
     * @apiParam (body) {string} [name] 名称
     * @apiParam (body) {string} url 长域名
     * @apiSuccessExample Success-Response
     *       {
     *           "_id": "xxxxxx",
     *           "url": "xxxxxx",
     *           "name": "xxxxxx",
     *           "compressed": "xxxxxx",
     *           "createdAt": "xxxxxx",
     *           "updatedAt": "xxxxxx"
     *       }
     */
    .post(asyncHandler(async (req, res) => {
        res.success(await DomainService.addDomain(req.body.url, req.body.name));
    }))

    /**
     * @api {get} /api/v1/domain 查询
     * @apiGroup 域名映射
     * @apiVersion 1.0.0
     * @apiParam (query) {string} shortUrl 短域名
     * @apiSuccessExample Success-Response
     *       {
     *           "_id": "xxxxxx",
     *           "url": "xxxxxx",
     *           "name": "xxxxxx",
     *           "compressed": "xxxxxx",
     *           "createdAt": "xxxxxx",
     *           "updatedAt": "xxxxxx"
     *       }
     */
    .get(asyncHandler(async (req, res) => {
        res.success(await DomainService.getDomainByShortUrl(req.query.shortUrl));
    }));

export default router;
