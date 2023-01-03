import request from 'supertest';
import { Express } from 'express-serve-static-core';

import { createServer } from '../server';

describe('createServer', () => {
  let server: Express;
  beforeAll(async () => {
    server = await createServer();
  });

  it('should have default health api', async () => {
    const response = await request(server).get('/').expect('Content-Type', /json/).expect(200);

    expect(response.headers['content-type']).toMatch(/json/);
    expect(response.status).toEqual(200);
    expect(response.body.message).toEqual('Ok');
  });
});
