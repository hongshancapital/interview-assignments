import express, { Application, Request, Response, NextFunction } from 'express';
import cors from 'cors';
import helmet from 'helmet';
import Url from '../db/url.model';

const app: Application = express();
app.use(helmet());
app.use(cors());
app.use(express.json());

const router = express.Router();
// @route           GET /:urlCode
// @description     Redirect to the original URL 
router.get('/:urlCode', async (req: Request, res: Response, next: NextFunction) => {
    const { urlCode } = req.params;

    try {
        const url = await Url.findOne({ urlCode: urlCode });

        if (url) {
            res.redirect(url.originalUrl);
        } else {
            res.status(404).json('No URL Found');
        }
    } catch (error) {
        console.error(error);
        res.status(500).json('Server Error');
    }
});

module.exports = router;