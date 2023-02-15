import express, { Application, Request, Response } from "express";
import { IRoute, Router } from "express-serve-static-core";
import conn from "./db";
import { map } from "./helper";

var generatorRouter: Router = express.Router();
const INVALID_GENERATOR_MESSAGE: string = "Failed to generate Tiny URL";
const sqlGenerator: string = "INSERT INTO t_url(targeturl) VALUES(?)";
const sqlUpdateTinyUrl: string = "UPDATE t_url SET tinyurl = ? WHERE id = ?";

generatorRouter.get(
    "/_generate",
    async function (req: Request, res: Response, next: Function) {
        conn.query(sqlGenerator, [req.query.t], (err: any, result: any) => {
            if (err) {
                console.log(err);
                res.send(INVALID_GENERATOR_MESSAGE);
            } else if (result.affectedRows < 1) {
                res.send(INVALID_GENERATOR_MESSAGE);
            } else {
                var lastInsertId:number = result.insertId;
                var tinyUrl:string = map(lastInsertId-1);
                if (tinyUrl=="") {
                    res.send(INVALID_GENERATOR_MESSAGE);
                }
                conn.query(sqlUpdateTinyUrl, [tinyUrl, lastInsertId], (err: any, result: any) => {
                    if (err) {
                        console.log(err);
                        res.send(INVALID_GENERATOR_MESSAGE);
                    } else if (result.affectedRows < 1) {
                        res.send(INVALID_GENERATOR_MESSAGE);
                    } else {
                        res.send(tinyUrl);
                    }
                });
            }
        });
    }
);

export default generatorRouter;
