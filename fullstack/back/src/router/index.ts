import express, { Request, Response } from 'express';
import SnowFlake from '../util/snowflake';
import { string10to62 } from '../util/base';
import { UrlModel, UrlData } from '../model/url';
import { SHORE_BASE, DBQuery } from '../data/constant';
import md5 from 'blueimp-md5';

const indexRoute: express.Router = express.Router();

// url 转换
indexRoute.post('/', async (req: Request, res: Response) => {
    let search: string = req.body['search'];
    let isShort = search.length <= SHORE_BASE.length + 8;
    
    // 高级函数 方便动态查询
    async function queryModel(query: DBQuery): Promise<UrlData | null> {
        let db_url: UrlData | null = await UrlModel.findOne(query);
        return db_url;
    }

    // 情况1: 短链接 + 无缓存
    if ( isShort &&
        await queryModel({ "short_url": search.substring(SHORE_BASE.length) }) == null) {
        res.status(200).json({
            code: 20000,
            message: "not find",
            data: {
                url: null,
            }
        });
        return;
    }

    let hash_url = md5(search);   
    // 情况2: 长链接 + 无缓存
    if (!isShort && 
        await queryModel({ "hash_url": hash_url }) == null) {
        let snowflake = new SnowFlake(1n, 1n);                 // 雪花算法 保证唯一性
        let short_url = string10to62(snowflake.nextId());      // 转为短链接
        await UrlModel.create({ short_url, long_url: search, hash_url });
        res.status(200).json({
            code: 0,
            message: "success",
            data: {
                isShort,
                url: `${SHORE_BASE}${short_url}`,
            }
        });
        return;
    }

    // 情况3: 有缓存
    let query = isShort ? 
                { "short_url": search.substring(SHORE_BASE.length)} : 
                { "hash_url": hash_url };
    let db_url: UrlData | null = await queryModel(query)
    if (db_url != null) {
        console.log(db_url);
        let result = isShort ? `${db_url?.long_url}` : `${SHORE_BASE}${db_url?.short_url}`
        res.status(200).json({
            code: 0,
            message: "success",
            data: {
                isShort,
                url: result,
            }
        });
        return;
    } else {
        res.status(502).json({
            code: 20000,
            message: "error",
            data: {
                url: null,
            }
        });
    }   
})

export default indexRoute;

