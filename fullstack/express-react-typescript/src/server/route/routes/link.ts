import router from '../router';
import { Test } from '../../models';
import { Request, Response } from "express";
import { ILink } from "../../domain/ILink";
import { IError } from '../../domain/IError';
import { generate } from '../../untils/shortIdGen';
import url from 'url';


// 根据短链接获取长链接
router.route('/getLongLink')
    .post(async (req: Request, res: Response) => {
        const { shortLink }: { shortLink: string } = req.body;
        try {
            // 有短链接，则直接获取长链接返回
            const oneObj = await Test.findOne({ shortLink })
            if (oneObj) {
                res.status(202).json(oneObj);
            } else {
                const error: IError = {
                    status: 500,
                    message: "数据异常!"
                }
                res.status(error.status).json({ message: error.message });
            }
        } catch (ex) {
            res.status(500).json(ex);
        }
    })

// 根据长链接获取短链接
router.route('/getShortLink')
    .post(async (req: Request, res: Response) => {
        const { longLink }: { longLink: string } = req.body;

        // 校验url合法性
        const {
            protocol,
            host,
            port
        } = url.parse(longLink)

        if (!protocol || !host) {
            // 长链接不合法
            res.status(500).json({ message: "长链接格式不对" });
        } else {

            try {
                // 根据长链接查询是否有短链接，有则返回。
                const oneObj = await Test.findOne({ longLink })
                if (oneObj) {
                    res.status(200).json(oneObj);
                    return
                } else {
                    // 没则生成短链接并入库。然后，返回短链接。
                    // 生成短链接path
                    const shortPath: string = generate(1, 8);
                    const _port = port ? `:${port}` : ''
                    const _shortLink = `${protocol}//${host}${_port}/${shortPath}`;
                    const Text: ILink = new Test({ longLink, shortLink: _shortLink });
                    const savedText: ILink = await Text.save();
                    res.status(201).json(savedText);
                }

            } catch (e) {
                const error: IError = {
                    status: 500,
                    message: "有异常!"
                }
                res.status(error.status).json({ message: "有异常" });
            }
        }
    })

export default router;
