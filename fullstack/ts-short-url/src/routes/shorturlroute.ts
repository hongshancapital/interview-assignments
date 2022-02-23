import { NextFunction, Request, Response, Router } from "express";

import { ShortUrlReq } from "../request/shorturlreq";
import { OriginalUrlReq } from "../request/originalurlreq";
import { ShortUrlService } from "../service/ShortUrlService"
import { ShortUrlRes } from "../response/shorturlres";
import { OriginalurlRes } from "../response/originalurlres";
import { ServerCode } from "../exception/errorcode";


export class ShortUrlRoute {

    private shortUrlService: ShortUrlService;

    constructor() {
        this.shortUrlService = new ShortUrlService();
    }

    public static create(router: Router) {
        // 注册短链接申请服务
        router.post('/ShortUrl', function(req, res, next) {
            console.log("/ShortUrl req.body is", req.body);
            new ShortUrlRoute().ShortUrlPro(req, res, next);
        });

        // 注册原链接查询服务
        router.post('/OriginalUrl', function(req, res, next) {
            console.log("/OriginalUrl req.body is", req.body);
            new ShortUrlRoute().OriginalUrlPro(req, res, next);
        });
    }
 
    // 处理短链接申请请求
    public ShortUrlPro(req: Request, res: Response, next: NextFunction) {
        let success: boolean = false;
        let errCode:ServerCode = ServerCode.SU_SERVER_ERROR;
        let shorturl: string = "";
        let shortreq: ShortUrlReq = new ShortUrlReq();
        try {
            let init: boolean = shortreq.init(req.body);
            if (init) {
                shorturl = this.shortUrlService.queryOrGenerateShortUrl(shortreq.getOriginalUrl());
                if (shorturl != "") {
                    errCode = ServerCode.SU_SUCCES;
                    success = true;
                }
            }
        } catch (error) {
            errCode = ServerCode.SU_REQ_ARG_ERROR;
        }

        res.json((new ShortUrlRes(success, errCode, shorturl)).buildJson());
    }

    // 处理长链接查询请求
    public OriginalUrlPro(req: Request, res: Response, next: NextFunction) {
        let success: boolean = false;
        let errCode:ServerCode = ServerCode.SU_SERVER_ERROR;
        let originalUrl: string = "";
        let originalReq: OriginalUrlReq = new OriginalUrlReq();
        try {
            let init: boolean = originalReq.init(req.body);
            if (init) {
                originalUrl = this.shortUrlService.queryOriginalUrl(originalReq.getShortUrl());
                if (originalUrl != "") {
                    errCode = ServerCode.SU_SUCCES;
                    success = true;
                }
            }
        } catch (error) {
            errCode = ServerCode.SU_REQ_ARG_ERROR;
        }

        res.json((new OriginalurlRes(success, errCode, originalUrl)).buildJson());        
    }
}