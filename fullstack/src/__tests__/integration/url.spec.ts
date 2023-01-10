import 'dotenv/config';

import { MongoClient } from 'mongodb';
import mongoose from 'mongoose';
import request from 'supertest';
import faker from 'faker';

import app from '@bootstrap/app';

describe('Url module', () => {
  it('should create a shorten url', async () => {
    const url = faker.internet.url();
    const response = await request(app).post('/api/createShortUrl').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toHaveProperty('shortUrl');
  });


  it('should get original url by short url', async () => {
    const url = faker.internet.url();
    const createRes = await request(app).post('/api/createShortUrl').send({ url });

    expect(createRes.status).toBe(200);
    expect(createRes.body).toHaveProperty('shortUrl');

    const findRed = await request(app).get('/api/getOriginUrl').query({
      shortUrl: createRes.body.shortUrl
    })

    expect(findRed.status).toBe(200);
    expect(findRed.body).toHaveProperty('originUrl');
    expect(findRed.body.originUrl).toBe(url);
  });
});
