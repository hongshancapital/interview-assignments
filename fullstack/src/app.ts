import { redisClient, mysqlConnection } from './connection';
import { Service } from './service';
import express, { Express, Request, Response } from 'express';
import bodyParser from 'body-parser';
import { formatURL, isURL } from './utils';
import { Cache } from './cache';
import { BloomFilter } from './bloomfilter';
import { ShortLinkRepository } from './db';


const app: Express = express();

app.use(bodyParser.json());

const service = new Service(new Cache(redisClient), new BloomFilter(redisClient), new ShortLinkRepository(mysqlConnection))

app.on('ready', async () => {
    service.init()
})

app.post('/shortlink', async (req: Request, res: Response) => {
    let url: string = req.body.url;
    if (isURL(url)) {
        url = formatURL(url)
        let hash: string | number = await service.urlHash(url)
        if (typeof hash == 'string') {
            res.json({
                hash
            })
        } else {
            res.status(500).json({ error: 'server error' })
        }
    } else {
        res.status(400).json({ error: 'please send correct url' })
    }
});

app.get('/shortlink', async (req: Request, res: Response) => {
    let hash: string = req.query.hash as string;
    if (!hash) {
        res.status(400).json({
            error: 'wrong hash'
        })
        return
    }
    let url: string | number = await service.hashUrl(hash)
    if (typeof url == 'string') {
        res.json({
            url
        })
    } else {
        switch (url as number) {
            case 0:
                res.json({
                    url: ''
                })
                break
            case -1:
                res.status(400).json({
                    error: 'wrong hash'
                })
                break
            case -2:
                res.status(500).json({
                    error: 'server error'
                })
        }
    }
})

export { app }
