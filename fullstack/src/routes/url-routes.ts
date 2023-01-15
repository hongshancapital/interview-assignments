import HttpStatusCodes from "../../src/declarations/major/HttpStatusCodes";

import urlService, {urlNotFoundErr} from "../../src/services/url-service";
import {IReq, IRes} from "./shared/types";
import {isValidOriginalUrl, isValidShortUrl} from "../../src/declarations/functions";
import {RouteError} from "../../src/declarations/classes";

// **** Variables **** //

// Messages
const urlInvalidErr = "Url is invalid!";
// Paths
const paths = {
  basePath: "/urls",
  getShortUrl: "/short",
  getOriginalUrl: "/original",
} as const;

// **** Functions **** //

/**
 * Get shortUrl.
 */
async function getShortUrl(req: IReq<{originalUrl: string}>, res: IRes) {
  const {originalUrl} = req.body;
  if (!originalUrl || !isValidOriginalUrl(originalUrl)) {
    throw new RouteError(HttpStatusCodes.BAD_REQUEST, urlInvalidErr);
  }
  const url = await urlService.getShortUrlInfo(originalUrl);
  return res.status(HttpStatusCodes.OK).json({shortUrl: url.shortUrl});
}
/**
 * Get originalUrl.
 */
async function getOriginalUrl(req: IReq<{shortUrl: string}>, res: IRes) {
  const {shortUrl} = req.body;
  if (!shortUrl || !isValidShortUrl(shortUrl)) {
    throw new RouteError(HttpStatusCodes.BAD_REQUEST, urlInvalidErr);
  }
  const url = await urlService.getOriginalUrlInfo(shortUrl);
  if (!url) {
    // 此处直接404简单处理，实际业务可能为200，利用返回体code码区分
    res.status(HttpStatusCodes.NOT_FOUND).json({error: urlNotFoundErr});
  }
  return res.status(HttpStatusCodes.OK).json({originalUrl: url?.originalUrl});
}

// **** Export default **** //

export default {
  paths,
  getShortUrl,
  getOriginalUrl,
} as const;
