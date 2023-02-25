import { RequestHandler } from "express";
import BadRequestError from "../errors/BadRequestError";
import ShortUrlService from "../service/shorUrlService";
import { Controller } from "./types";

class ShortUrlController  implements Controller {
  private sortUrlService: ShortUrlService = new ShortUrlService();

  public path = '/short_url';

  post: RequestHandler = (req, _res,  next) => {
    const url = req.body.url;
    if (!url) {
      return next(new BadRequestError());
    }
    const shorten = this.sortUrlService.generalSortUrl(<string>url);
    return next({
      shorten
    });
  }

  get: RequestHandler = (req, _res, next) => {
    const shorten = req.query.shorten;
    if (!shorten) {
      return next(new BadRequestError());
    }
    const url = this.sortUrlService.getOriginalUrl(<string>shorten);
    return next({
      url
    });
  }
}

export default new ShortUrlController();