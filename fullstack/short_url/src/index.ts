import express from 'express';
import bodyParser from 'body-parser';

import { getShortUrl, getOrginalUrl } from './short_url_service';

const app = express();
const port = 3000;

app.use(bodyParser.json());

app.post('/', (req, res) => {
    getShortUrl(req.body.url)
    .then((shortUrl) => res.send(shortUrl))
    .catch(e => {
        res.status(500);
        res.send();
        console.log(e);
    });
});

app.get('/:short_url', (req, res) => {
    getOrginalUrl(req.params.short_url)
    .then((url) => {
        if (url) {
            res.send(url);
        }else {
            res.status(404);
            res.send();
        }
    })
    .catch(e => {
        res.status(400);
        res.send();
        console.log(e);
    });
});

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`);
});
