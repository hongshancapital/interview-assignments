import { NextFunction, Request, Response, Router } from "express";
import { validationResult } from "express-validator"

import { ServerCode } from "../exception/errorcode"
import { ServerError } from "../exception/servererror"

export class ShortUrlReq {

    private originalUrl: string = "";

    constructor() {}

    public initByReq(req: Request): boolean {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return false;
        }
        this.originalUrl = req.body.originalUrl;                
        return true;
    }

    public getOriginalUrl(): string {
        return this.originalUrl;
    }
}