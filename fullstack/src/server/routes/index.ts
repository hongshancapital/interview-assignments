import UrlModel, {UrlDocument} from "../model/Url";
import express, { Request, Response }  from "express";
import { nanoid } from "nanoid";
import dbConnect from "../lib";

const app = express();

// 短链接存储接口
app.post("/api/shortUrls", async (req: Request, res: Response) => {
    console.log('api')
    const {longUrl} = req.body;
    await dbConnect();
    const existingUrl:UrlDocument | null = await UrlModel.findOne({longUrl}).exec();
    if (existingUrl) {
        res.send({shortUrl: existingUrl.shortUrl});
        return;
    }
    const shortUrl = nanoid(6);
    const newUrl = new UrlModel({
        longUrl,
        shortUrl,
    });
    await newUrl.save();
    res.send({shortUrl});
});

// 短链接读取接口
app.get("/:shortUrl", async (req: Request, res: Response) => {
    const {shortUrl} = req.params;
    await dbConnect();
    const existingUrl: UrlDocument | null = await UrlModel.findOne({shortUrl}).exec();
    if (existingUrl) {
        res.redirect(existingUrl.longUrl);
    } else {
        res.status(404).send("Not found");
    }
});

export default app;
