import express from 'express';
import bodyParser from 'body-parser';
import Util from './util.js';
import urlService from './urlService.js';

const app = express();
const port = 8080;

app.use(bodyParser.json({ limit: '65kB' }));

app.post('/longToShort', async (req, res) => {
    const { url } = req.body;
    if (!url) {
        return res.status(Util.HTTP_CODE.INVALID_PARAM).end();
    }
    const result = await urlService.longToShort(url);
    return res.json({
        result
    });
});

app.post('/shortToLong', async (req, res) => {
    const { url } = req.body;
    if (!url) {
        return res.status(Util.HTTP_CODE.INVALID_PARAM).end();
    }
    const path = Util.getPath(url);
    console.log('short to long parsed path:', path);
    if (!path) {
        return res.status(Util.HTTP_CODE.NOT_FOUND).end();
    }
    const result = await urlService.shortToLong(path);
    if (result) {
        return res.json({
            result
        });
    } else {
        return res.status(Util.HTTP_CODE.NOT_FOUND).end();
    }
});

app.listen(port, () => {
    return console.log(`app is listening at http://localhost:${port}`);
});

app.use((error: Error, req: express.Request, res: express.Response, next: express.NextFunction) => {
    console.error('Express catches error:', error);
    return res.status(Util.HTTP_CODE.ERROR).end();
});