import express from 'express';
import asyncHandler from 'express-async-handler';

import { getContext } from '@/utils/context';
import { ShortUrlCreate } from './short-url.interface';
import * as shortUrlsService from './short-urls.service';
import { Request, Response } from 'express-serve-static-core';
import { AppError, HttpCode } from '@/common/app-error';

export const shortUrlsRouter = express.Router();
export const queryShortUrlOnlyRouter = express.Router();

const queryUrlHandler = async (req: Request, res: Response, shortId: string) => {
  const ctx = getContext(res);
  const shortUrl = await shortUrlsService.find(shortId, ctx);
  if (!shortUrl) {
    throw new AppError({
      httpCode: HttpCode.NOT_FOUND,
      description: 'URL Not Found',
    });
  }
  return shortUrl;
};

queryShortUrlOnlyRouter.get(
  /^\/(\w{8})\/?/,
  asyncHandler(async (req, res) => {
    const shortId = req.params[0];
    const shortUrl = await queryUrlHandler(req, res, shortId);
    res.redirect(shortUrl.url);
  })
);

shortUrlsRouter.get(
  '/:shortId',
  asyncHandler(async (req, res) => {
    const shortId = req.params.shortId;
    const shortUrl = await queryUrlHandler(req, res, shortId);
    res.json(shortUrl);
  })
);

shortUrlsRouter.post(
  '/',
  asyncHandler(async (req, res) => {
    const ctx = getContext(res);
    const shortUrlCreate: ShortUrlCreate = req.body;
    const shortUrl = await shortUrlsService.create(shortUrlCreate, ctx);
    res.json(shortUrl);
  })
);
