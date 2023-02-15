import express, { Application, Request, Response } from "express";
import { IRoute, Router } from "express-serve-static-core";
import conn from "./db";
import { BITS } from "./helper";

var tinyurlRouter: Router = express.Router();
const INVALID_TINYURL_MESSAGE: string = "Invalid Tiny URL";
const r = /^[A-Za-z0-9]+$/g;
const sqlGetTargetUrl: string = "SELECT targetUrl FROM t_url WHERE tinyUrl = ?";

tinyurlRouter.get(
    "/:url",
    async function (req: Request, res: Response, next: Function) {
        if (req.params.url.length > BITS) {
            // overlong tiny url
            res.send(INVALID_TINYURL_MESSAGE);
        } else if (!req.params.url.match(r)) {
            // invalid tiny url
            res.send(INVALID_TINYURL_MESSAGE);
        } else {
            conn.query(sqlGetTargetUrl, [req.params.url], (err: any, result: any) => {
                if (err) {
                    console.log(err);
                    res.send(INVALID_TINYURL_MESSAGE);
                } else if (result.length < 1) {
                    res.send(INVALID_TINYURL_MESSAGE);
                } else {
                    if (result[0].targetUrl.startsWith('http')) {
                        res.redirect(result[0].targetUrl);
                    } else {
                        res.redirect("http://" + result[0].targetUrl);
                    }
                }
            });
        }
    }
);

export default tinyurlRouter;
