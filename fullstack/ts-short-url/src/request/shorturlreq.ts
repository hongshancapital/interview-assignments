import { Request } from "express";
import { check, validationResult } from "express-validator"

export class ShortUrlReq {

    private originalUrl: string = "";

    constructor() {}

    public async initByReq(req: Request): Promise<boolean> {
        await check('originalUrl', 'originalUrl cannot be blank').isLength({ min: 1 }).run(req);
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