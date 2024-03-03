import { Router, Request, Response, NextFunction } from 'express';

import isURL from 'validator/lib/isURL';

import RestResponse from "../infra/RestResponse";

import ShortService, { ShortPO } from '../services/short';

import { HttpStatus } from "../infra/HttpStatus";

const router = Router();

export interface GetShortDTO {
  alias: string;
}

export interface InsertShortDTO {
  url: string;
}

router.get("/short/:alias", async function (req: Request<GetShortDTO>, res: Response<RestResponse<string>>) {
  try {
    const alias = req.params.alias;
    if (!alias) return res.status(HttpStatus.BAD_REQUEST).send(RestResponse.failure(-1, "Missing parameter."));
    const po = await ShortService.findOneShortByAlias(alias);
    if (!alias) return res.status(HttpStatus.NOT_FOUND).send(RestResponse.failure(-1, "Not Found."));
    res.send(RestResponse.ok(po.url));
  } catch (e) {
    res.status(HttpStatus.INTERNAL_SERVER_ERROR).send(RestResponse.failure(-1, "Server Exception."))
  }
});

router.post("/short", async function (req: Request<any, any, InsertShortDTO>, res: Response<RestResponse<string | null>>) {
  try {
    const url = req.body.url;
    if (!url) return res.status(HttpStatus.BAD_REQUEST).send(RestResponse.failure<null>(-1, "Missing parameter."));
    if (!isURL(url)) return res.status(HttpStatus.BAD_REQUEST).send(RestResponse.failure<null>(-1, "Invalid parameter."));
    const exist = await ShortService.findOneShortByURL(url);
    if (exist) return res.status(HttpStatus.OK).send(RestResponse.ok(`${exist.domain}/${exist.alias}`));
    const po = await ShortService.insertShort(url);
    return res.status(HttpStatus.CREATED).send(RestResponse.ok(`${po.domain}/${po.alias}`));
  } catch (e) {
    res.status(HttpStatus.INTERNAL_SERVER_ERROR).send(RestResponse.failure(-1, "Server Exception."))
  }
});

export default router;