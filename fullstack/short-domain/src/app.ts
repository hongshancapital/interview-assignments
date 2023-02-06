import express, { Express , Request, Response } from "express";
import {Snowflake} from "./utils/snowflake/snowflake";
import * as dotenv from 'dotenv';
// 加载环境变量
dotenv.config();
const app: Express = express();
const snowflakeGenerator = new Snowflake({
    workerId: process.env.WORKER_ID ? Number(process.env.WORKER_ID) : 1
});
/**
 * 通过短域名获取
 */
app.get("/:shortId", (req: Request, res: Response) => {
});

app.put("/add", (req: Request, res: Response) => {
    res.send(snowflakeGenerator.nextId());
})

app.listen(3000, () => {
    console.log("Server running on port 3000");
});
