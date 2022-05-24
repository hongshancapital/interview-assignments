import * as express from 'express';
import { BadRequestError, Body, Get, HttpCode, JsonController, NotFoundError, Param, Post, Res } from 'routing-controllers';
import { StatusCodes } from 'http-status-codes';
import { BASE_URL } from '../component/constant';
import { getRepository } from '../component/db';
import { CreateUrlRequest, UrlResponse } from '../component/types';
import { generate } from '../component/randomcode';
import { Url } from '../entity/url';

@JsonController()
export class UrlController {
  private readonly urlRepository = getRepository(Url);

  @Post('/v1/urls')
  @HttpCode(StatusCodes.CREATED)
  public async create(@Body() request: CreateUrlRequest): Promise<UrlResponse> {
    let url: Url;
    while (true) {
      try {
        url = await this.urlRepository.save(this.urlRepository.create({
          code: generate(),
          originalUrl: request.originalUrl,
        }));
      } catch (e) {
        if (e instanceof Error && e.message.includes('duplicate key')) {
          continue;
        } else {
          console.error(`Failed to create url`, e);
          throw new BadRequestError();
        }
      }
      break;
    }
    return {
      shortUrl: `${BASE_URL}/${url.code}`,
      originalUrl: request.originalUrl,
    };
  }

  @Get('/:code')
  public async redirect(@Param('code') code: string, @Res() res: express.Response): Promise<express.Response> {
    const url = await this.urlRepository.findOne({ code });
    if (!url) {
      throw new NotFoundError();
    }
    res.redirect(url.originalUrl);
    return res;
  }
}
