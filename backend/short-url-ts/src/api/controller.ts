import {Router} from 'express';
import {UrlService} from "./service";

const router = Router();
const urlService = new UrlService();

/**
 * 长链接 to 短链接
 */
router.put('/lToS', async (req, res) => {
    try {
        const longUrl = req.body.longUrl;
        let data = await urlService.putUrl(longUrl);
        res.send(new BasicMessage('200', 'ok', data));

    } catch (e) {
        console.error(e);
        res.send(new BasicMessage(e.errorCode, e.message));
    }
});

/**
 * 短链接 to 长链接
 */
router.get('/sToL', async (req, res) => {
    try {
        const shortUrl = req.query.shortUrl as string;
        let data = await urlService.getLongUrl(shortUrl);
        res.send(new BasicMessage('200', 'ok', data));

    } catch (e) {
        console.error(e);
        res.send(new BasicMessage(e.errorCode, e.message));
    }
});

/**
 * 基础响应消息格式
 */
class BasicMessage {
    constructor(public code: string, public message: string, public data?: string) {
    }
}

export default router;