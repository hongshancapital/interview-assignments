import express, { Express , Request, Response } from "express";
import {Snowflake} from "./utils/snowflake/snowflake";
import * as dotenv from 'dotenv';
import { addShortDomain, getShortDomain } from './dao/domainDao';
import bodyParser from 'body-parser';
import {ShortDomainItem} from "./schema/domainSchema";

// 加载环境变量
dotenv.config();
const app: Express = express();
const snowflakeGenerator = new Snowflake({
    workerId: process.env.WORKER_ID ? Number(process.env.WORKER_ID) : 1
});

app.use(bodyParser.json())


/**
 * 通过短域名获取
 */
app.get("/:shortId", (req: Request, res: Response) => {
    const { shortId } = req.params;
    if (!shortId || shortId.length !== 8) {
        throw new Error(`invalid short domain ${shortId}`);
    }
    // const shortDomainItem: ShortDomainItem = await getShortDomain(shortId);
    const shortDomainItem = {
        completeUrl: 'https://justinyan.me/post/4319'
    }
    res.redirect(301, shortDomainItem.completeUrl);
});

app.put("/add", (req: Request, res: Response) => {
    const { url } = req.body;
    if (!url) {
        throw new Error("the url can not be null");
    }
    // TODO 补充url check
    const snowId: string = snowflakeGenerator.next62Id();
    // await addShortDomain({
    //     unionId: snowId,
    //     completeUrl: url,
    //     createTime: +new Date()
    // })
    res.send(snowId);
})

// @ts-ignore
app.use((err, req, res, next) => {
    res.status(500);
    res.send(`Internal Server Error: ${err}`);
})

app.listen(3000, () => {
    console.log("Server running on port 3000");
});

