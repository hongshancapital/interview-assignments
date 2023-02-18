import {CHARSET, STATE,SEQ_STEP} from "../common/constant";
import {ShortUrl} from "../models/shortUrl";
import {Counter} from "../models/counter";

const base62 = require('base62/lib/custom');
const {NETWORK_PROTOCOL, DOMAIN} = process.env;

let _CHARSET: string = CHARSET;
_CHARSET = base62.indexCharset(_CHARSET);

export default class ShortDomainService {

    /**
     * 短链接找长的 方法
     * @param shortUrl 短链接
     */
    public static async shortToLong(shortUrl: string) {
        // 查询数据库中是否存在
        return ShortUrl.findOne({shortUrl: shortUrl, state: STATE.EFFECT}).lean();
    }


    /**
     * 长连接 返回 短链的 方法
     * @param oriUrl 原始链接
     */
    public static async longToShort(oriUrl: string) {
        // 查询数据库中是否存在
        const hasOne = await ShortUrl.findOne({oriUrl: oriUrl}).lean();
        if (hasOne) {
            return hasOne;
        }

        // 自增id生成的对应短码
        const obj = await Counter.findOneAndUpdate({_id: 'shortUrl'}, {$inc: {seq_val: SEQ_STEP}}, {
            upsert: true,
            new: true
        }).lean();

        // 原始地址 + 生成的短链
        const path = base62.encode(obj.seq_val, _CHARSET);
        return await new ShortUrl({
            oriUrl: oriUrl,
            shortUrl: `${NETWORK_PROTOCOL}${DOMAIN}/${path}`,
        }).save();
    }
}

