import express, {Express, json, urlencoded} from "express";
import dotenv from 'dotenv';
import cors from 'cors';
import {StoreShorterByLong} from "./controller/store_shorter";
import {GetLongByShorter} from "./controller/get_long";
import {SyncShortToRedis} from "./utils/redis_setup";

dotenv.config();

const Server: Express = express();
const serverPort = process.env.SERVER_PORT || 6789;

export const MAINFRAME_CODE = process.env.MAINFRAME_CODE || "AA";
export const GENERATE_LENGTH = process.env.GENERATE_LENGTH ? parseInt(process.env.GENERATE_LENGTH) : 8;

export const BASE_URL = process.env.BASE_URL || "x.xx/";

export const REDIS_URL = process.env.REDIS_URL || 'redis://localhost:6379';
export const EXPIRE_TIME = 3600;
export const GENERATOR_KEY = MAINFRAME_CODE + "-current-shortname";


export const Alphabet: string[] = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];

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
        console.log(`[server]: server is running at https://localhost:${serverPort}`);
    });

});

export default Server;
