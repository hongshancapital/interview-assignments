import { Request } from "express";
import { check, validationResult } from "express-validator"

export class OriginalUrlReq {

    private shortUrl: string = "";

    constructor() {}

    public async initByReq(req: Request): Promise<boolean> {
        await check('shortUrl', 'longUrl cannot be blank').isLength({ min: 1 }).run(req)
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