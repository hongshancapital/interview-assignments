/**
 * Required External Modules and Interfaces
 */
import express, { Request, Response } from "express";
import short_url_service from "../../../services/ShortUrlService"
import { BadRequestResponse, SuccessResponse, UnprocessableEntityResponse } from '../../../core/ApiContentResponse';
import { FoundRedirectResponse } from '../../../core/ApiRedirectResponse';
import { validateUrl, validateKey } from '../../../helpers/validator';
/**
 * Router Definition
 */
export const shortenUrlRouter = express.Router();
/**
 * Controller Definitions
 */

// 基于key获取已经生成的url
shortenUrlRouter.get("/:key", async (req: Request, res: Response) => {

    const key = req.params.key;
    if (!validateKey(key)) {
        return new BadRequestResponse("Invalid key.").send(res);
    }
    const url = short_url_service.findUrlByKey(key);
    if (url) {
        return new FoundRedirectResponse(url as string).send(res);
    }
    else {
        return new UnprocessableEntityResponse("Error: Unable to find URL to redirect to.").send(res);
    }

});

// 基于请求中的url 生成key
shortenUrlRouter.post("/", async (req: Request, res: Response) => {

    const url = req.body.url;
    if (!url) {
        return new BadRequestResponse("Url should not be empty.").send(res);
    }
    if (!validateUrl(url)) {
        return new BadRequestResponse("Invalid url.").send(res);
    }
    const key = short_url_service.shortenUrl(url);
    return new SuccessResponse('Successfully created.', key).send(res);

});
