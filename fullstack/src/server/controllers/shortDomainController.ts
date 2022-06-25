import {Request, Response, NextFunction} from "express";
import ShortDomainService from "../service/shortDomainService";

export default class ShortDomainController {
    /**
     * shortToLong 短的找长的
     * @param {*} req
     * @param {*} res
     * @param {*} next
     */
    public static async shortToLong(req: Request, res: Response, next: NextFunction) {
        try {
            // 此处应该有更多的参数效验， 演示demo就省略参数效验了。
            const {shortUrl}= req.query;
            if(!shortUrl){
                throw new Error('短地址shortUrl必须有');
            }

            let result = await ShortDomainService.shortToLong(shortUrl.toString());

            res.send({
                code: 0,
                message: '请求成功!',
                data: result
            });
        } catch (err) {
            res.send({
                code: 1,
                message: err.message ? err.message : '服务器好像出了点问题',
                ...err
            });
        }
    }

    /**
     * longToShort 长的找短的
     * @param {*} req
     * @param {*} res
     * @param {*} next
     */
    public static async longToShort(req: Request, res: Response, next: NextFunction) {
        try {
            // 此处应该有更多的参数效验， 演示demo就省略参数效验了。
            const {oriUrl}= req.query;
            if(!oriUrl){
                throw new Error('原地址oriUrl必须有');
            }

            let result = await ShortDomainService.longToShort(oriUrl.toString());

            res.send({
                code: 0,
                message: '请求成功！',
                data: result
            });
        } catch (err) {
            res.send({
                code: 1,
                message: err.message ? err.message : '服务器好像出了点问题',
                ...err
            });
        }

    }
}

