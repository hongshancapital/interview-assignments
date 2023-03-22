import {Request, Response, Router} from 'express'
import commonRes from '../utils/commonRes'
import {shortUrlModel} from '../models/shortUrl'
import {isValidHttpUrl} from "../utils";
import {nanoid} from 'nanoid'
import {logger} from "../../logs/log4j.config";

const router = Router()


/**
 * 查询和生成短链
 */
router.post('/gen', async (req: Request, res: Response) => {
    const {longUrl} = req.body;
    if (isValidHttpUrl(longUrl)) {
        try {
            let url = await shortUrlModel.findOne({longUrl});
            if (url) {
                commonRes(res, url)
            } else {
                const urlCode = nanoid(8);
                url = new shortUrlModel({
                    longUrl,
                    urlCode
                });
                await url.save();
                commonRes(res, url);
            }
        } catch (error) {
            commonRes.error(res, null, error)
        }
    } else {
        logger.warn(`无效链接:${longUrl}`)
        commonRes.error(res, null, '无效链接')
    }
});

/**
 * 根据短链查询长链
 */
router.get('/urlCode/:urlCode', async (req: Request, res: Response) => {
    const urlCode = req.params.urlCode;
    try {
        let url = await shortUrlModel.findOne({urlCode});
        if (url) {
            commonRes(res, url)
        } else {
            commonRes.error(res, null, `未找到对应长链信息！`)
        }
    } catch (error) {
        commonRes.error(res, null, error)
    }
});

// /**
//  * 查询所有链接信息
//  */
// router.get('/urls', async (req: Request, res: Response) => {
//     try {
//         const url = await shortUrlModel.find();
//         commonRes(res, {result: url})
//     } catch (error) {
//         commonRes.error(res, null, error)
//     }
// });


export default router
