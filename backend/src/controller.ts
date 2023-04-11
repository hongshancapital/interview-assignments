import {Request, Response,NextFunction, urlencoded} from 'express';
import { encode } from 'lex62ts';
import murmurhash from 'murmurhash';
import { UrlInfo } from './model';


 /**
 * 通过长id生成短url
 *
 * @param {Request} req
 * @param {Response} res
 */
 export const createShortUrl = async (req:Request, res:Response) => {
    const body = req.body || {};
    const {orginUrl} = body;
    const mid = murmurhash.v3(orginUrl, 62);
    const sid = encode(mid)
    const exists = await UrlInfo.findOne({_id:sid}).lean().exec();
    if(!exists){
        const url  = new UrlInfo({
            _id: sid,
            origin: orginUrl
            
        })
        await url.save();
    }
    const domain:String = 'http://short.url/'
    res.send(domain+sid)
}

/**
 *  通过短id获取
 *
 * @param {Request} req
 * @param {Response} res
 */
export const getOriginUrl = async (req:Request, res:Response) => {
    const {sid} = req.query;
    console.log('sid: ', sid);
    const result = await UrlInfo.findOne({_id:sid})
    res.send(result)
}