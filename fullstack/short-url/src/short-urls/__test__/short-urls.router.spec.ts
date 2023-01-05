import request from 'supertest';
import { Express } from 'express-serve-static-core';

import { Context, createMockContext, MockContext } from '@/utils/context';

import { createServer } from '@/utils/server';
import { encode } from '@/utils/short-id-converter';
import { ShortUrl } from '../short-url.interface';
import { shortUrlsRouter } from '../short-urls.router';
import * as shortUrlsService from '../short-urls.service';

jest.mock('../short-urls.service');
const mockedShortUrlsService = jest.mocked(shortUrlsService);

describe('short urls router', () => {
  let server: Express;
  let mockCtx: MockContext;
  let ctx: Context;

  beforeAll(async () => {
    mockCtx = createMockContext();
    ctx = mockCtx as unknown as Context;
    server = await createServer(ctx);
    server.use('/api/short-urls', shortUrlsRouter);
  });

  it('should get correct url when query by exists shortId', async () => {
    const shortId = encode(3);

    mockedShortUrlsService.find.mockReturnValue(
      Promise.resolve<ShortUrl>({
        id: 3,
        shortId,
        url: 'https://google.com',
      })
    );

    const response = await request(server).get(`/api/short-urls/${shortId}`).expect('Content-Type', /json/).expect(200);

    expect(shortUrlsService.find).toHaveBeenCalledWith(shortId, ctx);
    expect(response.body).toEqual({
      id: 3,
      shortId,
      url: 'https://google.com',
    });
  });

  it('should create new shortUrl when post new url', async () => {
    const shortId = encode(3);
    mockedShortUrlsService.create.mockReturnValue(
      Promise.resolve<ShortUrl>({
        id: 3,
        shortId,
        url: 'https://google.com',
      })
    );
    const response = await request(server)
      .post('/api/short-urls/')
      .send({ url: 'https://google.com' })
      .set('Accept', 'application/json')
      .expect('Content-Type', /json/)
      .expect(200);

    expect(shortUrlsService.create).toHaveBeenCalledWith({ url: 'https://google.com' }, ctx);
    expect(response.body).toEqual({
      id: 3,
      shortId,
      url: 'https://google.com',
    });
  });
});
