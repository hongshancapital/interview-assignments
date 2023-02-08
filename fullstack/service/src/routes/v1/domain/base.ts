import express from 'express';
import asyncHandler from 'express-async-handler';

import { errorType } from '@/configs';
import { check, isURL } from '@/utils';
import { Domain } from '@/services';
import { Domains } from '@/dal';

const router = express.Router();

router.route('/')
    /**
     * @api {post} /api/v1/domain 新增
     * @apiGroup 域名映射
     * @apiVersion 1.0.0
     * @apiParam (body) {string} [name] 名称
     * @apiParam (body) {string} url 长域名
     * @apiParam (body) {string} [prefix] 自定义前缀
     * @apiSuccessExample Success-Response
     *       {
     *           "_id": "xxxxxx",
     *           "url": "xxxxxx",
     *           "compressed": "xxxxxx",
     *           "type": "xxxxxx",
     *           "createdAt": "xxxxxx",
     *           "updatedAt": "xxxxxx"
     *       }
     */
    .post(asyncHandler(async (req, res) => {
        check(req.body, {
            name: { type: String },
            url: { type: String, notEmpty: true, required: true },
            prefix: { type: String }
        });
        const { name, url, prefix } = req.body as { name?: string, url: string, prefix?: string };

        if (!isURL(url)) {
            throw new Exception('invalid domain.', errorType.INVALID_ARGUMENTS);
        }
        if (await Domains.findByOriginalUrl(url)) {
            throw new Exception(`${url} already exists.`, errorType.FORBIDDEN);
        }
        if (prefix && !isURL(prefix)) {
            throw new Exception('invalid custom domain prefix.', errorType.INVALID_ARGUMENTS);
        }
        const shortDomain = Domain.generateShortDomain(url, prefix);

        if (new URL(shortDomain).pathname.length > 8) {
            throw new Exception('url is too long.', errorType.FORBIDDEN);
        }
        res.success(await Domains.insert({ name, url, compressed: shortDomain, type: prefix ? 'custom' : 'default' }));
    }))
    /**
     * @api {get} /api/v1/domain 分页查询
     * @apiGroup 域名映射
     * @apiVersion 1.0.0
     * @apiParam (query) {string} [name] 名称
     * @apiParam (query) {number} [skip] 跳过数,默认0
     * @apiParam (query) {number} [limit] 获取数,默认10
     * @apiSuccessExample Success-Response
     *       {
     *           "list": [{
     *               "_id": "xxxxxx",
     *               "url": "xxxxxx",
     *               "compressed": "xxxxxx",
     *               "type": "xxxxxx",
     *               "createdAt": "xxxxxx",
     *               "updatedAt": "xxxxxx"
     *           }],
     *           "total": 7
     *       }
     */
    .get(asyncHandler(async (req, res) => {
        check(req.query, {
            name: { type: String }
        });
        res.success(await Domains.getList({
            skip: Number(req.query.skip) || 0,
            limit: Number(req.query.limit) || 10,
            name: req.query.name as string | undefined
        }));
    }));


router.route('/:id').all((req, _res, next) => {
    if (!req.params.id) {
        throw new Exception('administrator account is required.', errorType.INVALID_ARGUMENTS);
    }

    next();
})
    /**
     * @api {get} /api/v1/domain/:id 单条数据查询
     * @apiGroup 域名映射
     * @apiVersion 1.0.0
     * @apiParam (params) {string} id 数据id
     * @apiSuccessExample Success-Response
     *       {
     *           "_id": "xxxxxx",
     *           "url": "xxxxxx",
     *           "compressed": "xxxxxx",
     *           "type": "xxxxxx",
     *           "createdAt": "xxxxxx",
     *           "updatedAt": "xxxxxx"
     *       }
     */
    .get(asyncHandler(async (req, res) => {
        res.success(await Domains.findById(req.params.id));
    }));
/** .delete(asyncHandler(async (req, res) => { })) */
/** .put(asyncHandler(async (req, res) => { })) */

export default router;
