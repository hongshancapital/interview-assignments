import { NextFunction, Request, Response, Router } from "express";
import { check } from "express-validator"

import { ShortUrlReq } from "../request/shorturlreq";
import { OriginalUrlReq } from "../request/originalurlreq";
import { ShortUrlService } from "../service/ShortUrlService"
import { ShortUrlRes } from "../response/shorturlres";
import { OriginalurlRes } from "../response/originalurlres";
import { ServerCode } from "../exception/errorcode";
import { ParamsDictionary } from "express-serve-static-core";
import { ParsedQs } from "qs";


export class ShortUrlRoute {

    private shortUrlService: ShortUrlService;

    constructor() {
        this.shortUrlService = new ShortUrlService();
    }

    public static create(router: Router) {
        // 注册短链接申请服务
        router.post('/ShortUrl', [ check('originalUrl').isLength({ min: 1 })], function(req: Request<ParamsDictionary, any, any, ParsedQs, Record<string, any>>, res: Response<any, Record<string, any>>, next: NextFunction) {
            new ShortUrlRoute().ShortUrlPro(req, res, next);
        });

        // 注册原链接查询服务
        router.post('/OriginalUrl', [ check('shortUrl').isLength({ min: 1 })], function(req: Request<ParamsDictionary, any, any, ParsedQs, Record<string, any>>, res: Response<any, Record<string, any>>, next: NextFunction) {
            new ShortUrlRoute().OriginalUrlPro(req, res, next);
        });
    }
 
    public async ShortUrlProSync(req: Request, res: Response, next: NextFunction) {
        
    }


    // 处理短链接申请请求
    public ShortUrlPro(req: Request, res: Response, next: NextFunction) {
        let issucc: boolean = false;
        let errCode:ServerCode = ServerCode.SU_SERVER_ERROR;
        let shorturl: string = "";
        let shortreq: ShortUrlReq = new ShortUrlReq();
        if (shortreq.initByReq(req)) {
            shorturl = this.shortUrlService.queryOrGenerateShortUrl(shortreq.getOriginalUrl());
            if (shorturl != "") {
                errCode = ServerCode.SU_SUCCES;
                issucc = true;
            } 
        } else {
            errCode = ServerCode.SU_REQ_ARG_ERROR;
        }
        res.json((new ShortUrlRes(issucc, errCode, shorturl)).buildJson());
    }

    // 处理长链接查询请求
    public OriginalUrlPro(req: Request, res: Response, next: NextFunction) {
        let issucc: boolean = false;
        let errCode:ServerCode = ServerCode.SU_SERVER_ERROR;
        let shorturl: string = "";
        let originalReq: OriginalUrlReq = new OriginalUrlReq();
        if (originalReq.initByReq(req)) {
            shorturl = this.shortUrlService.queryOriginalUrl(originalReq.getShortUrl());
            if (shorturl != "") {
                errCode = ServerCode.SU_SUCCES;
                issucc = true;
            } else {
                errCode = ServerCode.SU_SERVER_CAN_NOT_FIND_URL;
            }
        } else {
            errCode = ServerCode.SU_REQ_ARG_ERROR;
        }
        res.json((new OriginalurlRes(issucc, errCode, shorturl)).buildJson());
    }
}