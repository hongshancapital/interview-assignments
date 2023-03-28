import * as url from "url";

const express = require('express');
import {
    Request,
    Response
} from "express";

const dotenv = require('dotenv');
const bodyParser = require('body-parser')
const { body, validationResult } = require('express-validator');

import {generate} from './lib/shortUrl';
import {
    findByUrl,
    findByShort,
    create
} from './service/index';
import {ShortUrl} from "@prisma/client";

dotenv.config();

const app = express();
const port = process.env.PORT || 3000;
app.use(bodyParser.json()) // for parsing application/json
app.use(bodyParser.urlencoded({extended: true})) // for parsing application/x-www-form-urlencoded

app.get('/', (req: any, res: any) => {
    res.send('Express + TypeScript Server');
});

app.post('/shorturl', body('url').isURL(), async (req: Request, res: Response) => {
    const errors = validationResult(req)
    console.log(req.body.url, 'req.body.url')
    if (!errors.isEmpty()) {
        return res.status(200).json(
            errors
            // { errorCode: '-1', errorMessage: 'expect param: url' }
        );
    }
    const { url } = req.body
    const urlCode = generate(url)
    const [ err, record ] = await findByUrl(url)
    if (err) {
        return res.status(502).json({ errorMessage: 'server error 111' });
    }
    if (!record) {
        const [newError, newRecord ] = await create(urlCode, url)
        console.log('newRecord', newError)
        if (newError) {
            return res.status(200).json({
                // errorMessage: (newError as Error).message
                errorMessage: 'url exist'
            })
        }
        res.status(200).json({
            message: 'success',
            urlCode: (newRecord as ShortUrl).urlCode,
            longUrl: (newRecord as ShortUrl).longUrl
        })
    } else {
        res.status(200).json( record )
    }
})

app.get('/t/:short', async (req: any, res: any) => {
    const {short} = req.params
    const [ err, record ] = await findByShort(short)
    if (err) {
        console.log('errerrerrerr', err)
        res.status(200).json({
            errorMessage: 'not found'
        })
        return
    }
    if (!record) {
        res.status(200).json({
            errorMessage: 'not found'
        })
        return
    }
    res.status(200).json( record )
})

app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});


