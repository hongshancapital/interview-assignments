import { Express } from 'express-serve-static-core';
import express from 'express';
import bodyParser from 'body-parser';
import indexRouter from "../router";

export async function createServer(): Promise<Express> {
    const server = express()
    server.use(bodyParser.json())

    // 跨域设置
    server.all("*", function (req, res, next) {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "content-type");
        res.header("Access-Control-Allow-Methods", "DELETE,PUT,POST,GET,OPTIONS");
        next();
    })

    server.use(bodyParser.json());
    server.use(bodyParser.urlencoded({extended: true}));
    server.use('/', indexRouter);

    return server
}