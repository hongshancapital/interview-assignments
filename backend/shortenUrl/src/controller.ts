import { encode } from 'lex62ts';
import murmurhash from 'murmurhash';
import { UrlInfo } from './model';


 /**
 * 通过长id生成短url
 *
 * @param {string} originUrl
 */
const createShortUrl = async (originUrl:string):Promise<string> => {
    const mid = murmurhash.v3(originUrl, 62);
    const sid = encode(mid)
    const existUrlObj = await UrlInfo.findOne({_id:sid}).lean().exec();
    if(!existUrlObj){
        const url  = new UrlInfo({
            _id: sid,
            origin: originUrl
        })
        await url.save();
    } // 考虑到murmurhash的低碰撞率，暂时先不处理
    const shortUrl = 'http://short.url/' + sid // TODO: 放到config中
    return shortUrl
}

/**
 *  通过短url，获取原始url信息
 *
 * @param {string} sid
 */
const getOriginUrl = async (sid:string): Promise<any>=> {
    const result = await UrlInfo.findOne({_id:sid})
    return result
}

export {
    createShortUrl,
    getOriginUrl
}