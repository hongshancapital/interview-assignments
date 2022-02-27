import { Request, Response, NextFunction } from 'express';

import ShortUrlService from "../service/shorturlservice"
import { ShortUrlReq } from "../request/shorturlreq";
import { OriginalUrlReq } from "../request/originalurlreq";
import { ShortUrlRes } from "../response/shorturlres";
import { OriginalurlRes } from "../response/originalurlres";
import { ServerCode, apiErrorHandler } from "../exception/errorcode";


export default class ShortUrlCtrl {

    public async ShortUrlPro(req: Request, res: Response, next: NextFunction) {
        try {
            let shortreq: ShortUrlReq = new ShortUrlReq();
            let bCheckVal = await shortreq.initByReq(req);
            if (bCheckVal) {
                let strShortUrl = await ShortUrlService.queryOrGenerateShortUrl(shortreq.getOriginalUrl());
                if (strShortUrl != null) {
                    res.json((new ShortUrlRes(true, ServerCode.SU_SUCCES, strShortUrl)).buildJson());
                } else {
                    res.json((new ShortUrlRes(false, ServerCode.SU_SERVER_CREATE_SHORTURL_FAIL)).buildJson());
                }
            } else {
                res.json((new ShortUrlRes(false, ServerCode.SU_REQ_ARG_ERROR)).buildJson());
            }
        } catch (error) {
            apiErrorHandler(error, req, res, 'ShortUrl Pro failed.');
        }
    }

    public async OriginalUrlPro(req: Request, res: Response, next: NextFunction) {
        try {
            let originalReq: OriginalUrlReq = new OriginalUrlReq();
            let bCheckVal = await originalReq.initByReq(req);
            if (bCheckVal) {
                let strOriginalUrl = await ShortUrlService.queryOriginalUrl(originalReq.getShortUrl());
                if (strOriginalUrl != null) {
                    res.json((new OriginalurlRes(true, ServerCode.SU_SUCCES, strOriginalUrl)).buildJson());
                } else {
                    res.json((new OriginalurlRes(false, ServerCode.SU_SERVER_CAN_NOT_FIND_URL)).buildJson());
                }
            } else {
                res.json((new OriginalurlRes(false, ServerCode.SU_REQ_ARG_ERROR)).buildJson());
            }
        } catch (error) {
            apiErrorHandler(error, req, res, 'OriginalUrl Pro failed.');
        }        
    }
}
