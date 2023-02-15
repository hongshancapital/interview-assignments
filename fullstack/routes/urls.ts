import express, { Request, Response } from 'express';
// import { nanoid } from 'nanoid';
import dotenv from 'dotenv';
import ShortUrl from '../models/ShortUrl';
dotenv.config({ path: '../config/.env' });

const router = express.Router();

router.post('/create/short', async (req: Request, res: Response) => {
    const { origUrl } = req.body;
    const ID_LENG = process.env.ID_LENG || 8;
    // const urlId = nanoid(+ID_LENG);
    const urlId = 'asda';

    try {
        let url = await ShortUrl.findOne({ origUrl });

        if (url) {
            res.json(url);
        } else {
            const shortUrl = `${process.env.BASE}/${urlId}`;

            url = new ShortUrl({
                origUrl,
                shortUrl,
                urlId,
                date: new Date(),
            });

            await url.save();
            res.json(url);
        }
    } catch (error) {
        res.status(500).json('Server Error');
    }
});

export default router;