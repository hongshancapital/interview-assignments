/**
 * app 启动文件
 */
require('dotenv').config();
import express, {Express, Request, Response} from "express";
import {Snowflake} from "./utils/snowflake/snowflake";
import {addShortDomain, getShortDomain} from './dao/domainDao';
import bodyParser from 'body-parser';

export const app: Express = express();
const snowflakeGenerator = new Snowflake({
    workerId: process.env.WORKER_ID ? Number(process.env.WORKER_ID) : 1,
    workerIdBitLength: 3,
    seqBitLength: 3
});

app.use(bodyParser.json())


/**
 * 通过短域名获取
 */
app.get("/:shortId", (req: Request, res: Response) => {
    const {shortId} = req.params;
    if (!shortId || shortId.length !== 8) {
        throw new Error(`invalid short domain ${shortId}`);
    }
    getShortDomain(shortId)
        .then((url: string | undefined) => {
            if (!url) {
                res.status(404);
                res.send('no this id')
            } else {
                res.redirect(301, url);
            }
        })
        .catch(
            err => {
                throw new Error(err)
            }
        );

});

app.put("/add", (req: Request, res: Response) => {
    const {url} = req.body;
    if (!url) {
        throw new Error("the url can not be null");
    }
    // checkURL 合法性
    try {
        new URL(url);
    } catch (err) {
        throw new Error("invalid url");
    }
    const snowId: string = snowflakeGenerator.next62Id();
    addShortDomain({
        unionId: snowId,
        completeUrl: url,
        createTime: new Date()
    }).then(() => {
        res.send({
            unionId: snowId,
            url: `${process.env.BASE_DOMAIN}/${snowId}`
        });
    }).catch(err => {
        throw new Error(err);
    })
})

// @ts-ignore
app.use((err, req, res, next) => {
    res.status(500);
    res.send(`Internal Server Error: ${err}`);
})

if (process.env.NODE_ENV !== 'test') {
    app.listen(3000, () => {
        console.log("Server running on port 3000");
    });
}


