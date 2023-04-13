import UrlModel, {UrlDocument} from "../model/Url";
import express, {Request, Response} from "express";
import {nanoid} from "nanoid";
import dbConnect from "../lib";
import redis from '../lib/redis';
import {SHORT_DOMAIN} from "../constants";

const app = express();

// 短链接存储接口
app.post("/api/shortUrls", async (req: Request, res: Response) => {
    const {longUrl} = req.body;
    await dbConnect();

    // url -> shortURL 通过缓存防止恶意提交
    const url = await redis.get(SHORT_DOMAIN + longUrl);
    if (url) {
        console.info('命中缓存')
        return res.send({url});
    }

    const existingUrl: UrlDocument | null = await UrlModel.findOne({longUrl}).exec() as UrlDocument;
    if (existingUrl) {
        res.send({
            shortUrl: existingUrl.shortUrl
        });
        return;
    }

    const shortUrl = nanoid(7);
    const newUrl = new UrlModel({
        longUrl,
        shortUrl,
    });
    await newUrl.save();
    await redis.set(SHORT_DOMAIN + longUrl, shortUrl, 'EX', 6000);

    res.send({shortUrl});
});

// 短链接读取接口
app.get("/:shortUrl", async (req: Request, res: Response) => {
    const {shortUrl} = req.params;
    await dbConnect();
    const existingUrl: UrlDocument | null = await UrlModel.findOne({shortUrl}).exec() as UrlDocument;
    const {
        longUrl
    } = existingUrl;

    if (existingUrl) {
        res.redirect(longUrl);
    } else {
        res.status(404).send("Not found");
    }
});

export default app;
