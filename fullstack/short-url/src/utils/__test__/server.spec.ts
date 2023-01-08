import request from 'supertest';
import { Express } from 'express-serve-static-core';

import { createServer } from '../server';
import { Context, createMockContext } from '@/utils/context';

describe('createServer', () => {
  let server: Express;
  beforeAll(async () => {
    const mockCtx = createMockContext();
    const ctx = mockCtx as unknown as Context;
    server = await createServer(ctx, () => {});
  });

  it('should have default health api', async () => {
    const response = await request(server).get('/').expect('Content-Type', /json/).expect(200);

    expect(response.headers['content-type']).toMatch(/json/);
    expect(response.status).toEqual(200);
    expect(response.body.message).toEqual('Ok');
  });
});
