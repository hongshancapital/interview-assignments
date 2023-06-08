import express, { Application, Request, Response, NextFunction } from 'express';
import cors from 'cors';
import helmet from 'helmet';
import * as yup from 'yup';
import { nanoid } from 'nanoid';
import baseConfig from '../config/base.config';
import Url from '../db/url.model';

const app: Application = express();
app.use(helmet());
app.use(cors());
app.use(express.json());

// URL config
const urlCodeLength = 8;
const schema = yup.object().shape({
    originalUrl: yup.string().trim().url().required()
});

const router = express.Router();
// @route           POST /shortenUrl
// @description     Create short URL
router.post('/', async (req: Request, res: Response, next: NextFunction) => {
    let { originalUrl } = req.body;

    try {
        await schema.validate({ originalUrl });
        let url = await Url.findOne({ originalUrl: originalUrl });

        if (url) {
            res.json(url);
        } else {
            const urlCode = nanoid(urlCodeLength).toLowerCase();
            const shortUrl = `${baseConfig.BASE_URL}:${baseConfig.PORT}/${urlCode}`;

            url = new Url({
                urlCode,
                originalUrl,
                shortUrl
            });
            await url.save();

            res.json(url);
        }
    } catch (error: any) {
        console.error(error);
        if (error.message.startsWith('originalUrl')) {
            res.status(400).json(error.message);
        } else {
            res.status(500).json('Server Error');
        }
    }
});

module.exports = router;