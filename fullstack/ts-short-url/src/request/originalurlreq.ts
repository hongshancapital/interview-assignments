import { NextFunction, Request, Response, Router } from "express";
import { validationResult } from "express-validator"

import { ServerCode } from "../exception/errorcode"
import { ServerError } from "../exception/servererror"

export class OriginalUrlReq {

    private shortUrl: string = "";

    constructor() {}

    public initByReq(req: Request): boolean {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return false;
        }
        this.shortUrl = req.body.shortUrl;          
        return true;
    }
    
    public getShortUrl(): string {
        return this.shortUrl;
    }
}