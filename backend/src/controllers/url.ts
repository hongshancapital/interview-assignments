import { RequestHandler } from "express";
import * as UrlData from "../data/url"
import { responseJson } from "../util/response";
import { ErrorCodeOk } from "../biz/errors/bizerror";
import { joinUrl } from "../util/path";
import { logger } from "../util/log";
import { ErrorUrlOriginNotFound } from "../biz/errors/url";
import createHttpError from "http-errors";
import env from "../util/validateEnv";

const notFoundUrlError = createHttpError(404, "no such url")

// 读取短链
export const redirectOriginUrl: RequestHandler = async (req, res, next) => {
    try {
        logger.info(`redirecting... ${req.params.short}`)
        res.redirect(302, await UrlData.getOriginUrlFromCache(req.params.short));
    } catch (error) {
        if (error === ErrorUrlOriginNotFound) {
            next(notFoundUrlError)
        } else {
            next(error);
        }
    }
}

// 生成短链
export const addNewUrl: RequestHandler<unknown, unknown, UrlData.NewUrlRequest, unknown> = async (req, res, next) => {
    try {
        const newUrl = await UrlData.newUrl(req.body);
        const data = {
            url: joinUrl(env.HOST_HTTPS, env.HOST_DOMAIN, newUrl.shortenUrl),
            originUrl: newUrl.originUrl,
            description: newUrl.description,
            creator: newUrl.creator,
            expires: newUrl.expires,
            createdAt: newUrl.createdAt,
        }
        res.status(201).json(responseJson("", ErrorCodeOk, data));
    } catch (error) {
        next(error);
    }
}