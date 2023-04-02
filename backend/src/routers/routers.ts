import { Router ,Request, Response} from 'express';
import Redis from 'ioredis';
import config from "../config/config";
import ShortUrlService from "../service/ShortUrlService";
import Util from "../util/Util";
let shortUrlServiceModel = new ShortUrlService();
const router: Router = Router();

const redis = new Redis(config.redis);

// 生成短域名的接口
router.post('/api/shorten', async (req:Request, res:Response) => {

    try{
        const longUrl = req.body.url;
        // 检查长 URL 是否有效
        if (!longUrl || !Util.isValidUrl(longUrl) || longUrl.length > 2038) {
            res.status(400).json({ message: 'invalid url' });
            return;
        }

        let doc:any = await  shortUrlServiceModel.getShortIdByLongUrl(longUrl);
        if(!!doc && doc.shortId){
            res.json({ shortId: doc.shortId });
            return;
        }
        let shortKey = await redis.spop(config.rediskey.shortIdPool);
        if(shortKey === null ){
            res.status(500).json({ message: 'Server is  busy' });
            return;
        }

        let doc2:any = await shortUrlServiceModel.saveUrl(longUrl, shortKey);
        await redis.set(shortKey, longUrl, 'EX', 60 * 60 * 24 * 7);
        res.json({ shortId: shortKey });
    }catch (e) {
        res.status(500).json({ message: 'Server Error' });
    }


});
// 获取短域名的接口
router.get('/key/:shortId', async (req:Request, res:Response) => {

    try{
        let shortId = req.params.shortId;
        if(!shortId || typeof shortId !== 'string' || shortId.length > 8){
            res.status(400).json({ message: 'invalid key' });
            return ;
        }

        // 先从 Redis 中查找
        let longUrl = await redis.get(shortId);
        if (longUrl) {
            res.json({ url: longUrl });
            return ;
        }
        let doc:any = await shortUrlServiceModel.getLongUrlByShortId(shortId);
        if(!doc || doc.longUrl === null){
            res.status(400).json({ message: 'not found' });
            return ;
        }
        await redis.set(shortId, doc.longUrl, 'EX', 60 * 60 * 24 * 7);
        res.json({ url: doc.longUrl });

    }catch (e) {
        res.status(500).json({ message: 'Server Error' });
    }

});

export { router };