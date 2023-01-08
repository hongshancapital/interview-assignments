import express, { Request, Response } from 'express';
import ShortUrl from '../models/ShortUrl';
const router = express.Router();

router.get('/:urlId', async (req: Request, res: Response) => {
    try {
        const { urlId } = req.params;
        const url = await ShortUrl.findOne({ urlId });
        if (url) {
            await ShortUrl.updateOne({
                urlId,
            }, {
                $inc: {
                    clicks: 1
                }
            });
            return res.redirect(url.origUrl);
        } else {
            res.status(404).json('404');
        }
    } catch (err) {
        res.status(500).json('Server Error');
    }
});

export default router;