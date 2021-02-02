import { Request, Response, NextFunction } from 'express';
import * as is_http_url from 'is-http-url';
import {
  is_valid_short_url,
} from './utils';
import {
  STATUS_CODE_BAD_REQUEST,
} from '../constants';
import { SHORT_URL_PREFIX } from '../config';
import { logger } from '../logger';

export default class Validator {
  static on_create_request(req: Request, res:Response, next:NextFunction) : void {
    const { original_url } = req.body;
    logger.debug(`Validator::on_create_request called(): original_url='${original_url}'`);
    if ((original_url === undefined)
            || !is_http_url(original_url)
            || is_valid_short_url(original_url, SHORT_URL_PREFIX)) {
      res.status(STATUS_CODE_BAD_REQUEST).send('Bad Request: Invalid Original URL');
    } else next();
  }

  static on_resolve_request(req: Request, res:Response, next:NextFunction) : void {
    const { short_url } = req.params;
    logger.debug(`Validator::on_resolve_request called(): short_url='${short_url}'`);
    if (is_valid_short_url(short_url, SHORT_URL_PREFIX)) { next(); } else res.status(STATUS_CODE_BAD_REQUEST).send('Bad Request: Invalid Short URL');
  }
}
