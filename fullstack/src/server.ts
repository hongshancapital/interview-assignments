import express, {Express, json, urlencoded} from "express";
import dotenv from 'dotenv';
import cors from 'cors';
import {StoreShorterByLong} from "./controller/store_shorter";
import {GetLongByShorter} from "./controller/get_long";
import {SyncShortToRedis} from "./utils/redis_setup";

dotenv.config();

const Server: Express = express();
const serverPort = process.env.SERVER_PORT || 6789;

export const BaseUrl = process.env.BASE_URL || "x.xx/";
export const ExpireTime = 10;

(async () => {
    // redis
    await SyncShortToRedis()
})().then(() => {
    // cross-domain access
    Server.use(cors());
    Server.use(urlencoded());
    Server.use(json())

   // 短域名存储接口：接受长域名信息，返回短域名信息
    Server.post('/long-to-short', StoreShorterByLong);

    // 短域名读取接口：接受短域名信息，返回长域名信息
    Server.get('/short-to-long', GetLongByShorter);

    Server.listen(serverPort, () => {
        console.log(`[server]: sync redis data complete!`);
        console.log(`[server]: server is running at https://localhost:${serverPort}`);
    });

});

export default Server;
