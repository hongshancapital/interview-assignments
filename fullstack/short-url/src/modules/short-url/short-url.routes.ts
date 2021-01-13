import express = require('express');
import { StatusCode } from '../../core/enums/status-code.enum';
import { IResponse } from '../../core/interfaces/i-response.interface';
import { IShortUrlInfo } from './interfaces/i-short-url-info.interface';
import { shortUrlService } from './short-url.service';

const router = express.Router();

export const shortUrlRoutes = [
  router.post(
    '/create',
    async (
      req: express.Request<
        null,
        IResponse<IShortUrlInfo>,
        null,
        {
          url: string;
        }
      >,
      res,
    ) => {
      const url = req.query.url;
      let response: IResponse<IShortUrlInfo>;
      try {
        const shortUrlInfo = await shortUrlService.createShortUrl(url);
        response = {
          data: shortUrlInfo,
          statusCode: StatusCode.SUCCESS,
        };
      } catch (error) {
        response = {
          statusCode: StatusCode.INTERNAL_SERVER_ERROR,
          error: error.message,
        };
      }
      res.send(response);
    },
  ),
  router.get(
    '/:key',
    async (
      req: express.Request<
        {
          key: string;
        },
        IResponse<IShortUrlInfo>,
        null,
        null
      >,
      res,
    ) => {
      const key = req.params.key;
      let response: IResponse<IShortUrlInfo>;
      try {
        const shortUrlInfo = await shortUrlService.getShortUrl(key);
        if (!shortUrlInfo) {
          response = {
            statusCode: StatusCode.NOT_FOUND,
            error: 'not found',
          };
        } else {
          response = {
            data: shortUrlInfo,
            statusCode: StatusCode.SUCCESS,
          };
        }
      } catch (error) {
        response = {
          statusCode: StatusCode.INTERNAL_SERVER_ERROR,
          error: error.message,
        };
      }
      res.send(response);
    },
  ),
];
