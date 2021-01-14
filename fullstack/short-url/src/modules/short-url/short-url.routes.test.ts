import express = require('express');
import request = require('supertest');
import { appInit } from '../../app';
import { StatusCode } from '../../core/enums/status-code.enum';
import { getDbConnection } from '../../libs/db.lib';
import { redisClient } from '../../libs/redis.lib';

let app: express.Application;

beforeAll(async (done) => {
  app = await appInit();
  done();
});

const invalidKey = '111111111';
const notFoundKey = 'ZZZZZZZZ';
const invalidUrl = 'invalidUrl';
const randomUrl = `https://test.domain/${new Date().getTime()}`;
let createdKey: string;

describe('Short Url Endpoints Step 1', () => {
  it(`[GetShortUrl] The short url is invalid: ${invalidKey}`, async (done) => {
    request(app)
      .get(`/${invalidKey}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.INTERNAL_SERVER_ERROR,
          error: 'invalid key',
        });
        done();
      });
  });
  it(`[GetShortUrl] The short url is not found: ${notFoundKey}`, async (done) => {
    request(app)
      .get(`/${notFoundKey}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.NOT_FOUND,
          error: 'not found',
        });
        done();
      });
  });
  it(`[CreateShortUrl] The url is empty`, async (done) => {
    request(app)
      .post(`/create`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.INTERNAL_SERVER_ERROR,
          error: 'miss param url',
        });
        done();
      });
  });
  it(`[CreateShortUrl] The url is invalid: ${invalidUrl}`, async (done) => {
    request(app)
      .post(`/create?url=${encodeURIComponent(invalidUrl)}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.INTERNAL_SERVER_ERROR,
          error: 'invalid url',
        });
        done();
      });
  });
  it(`[CreateShortUrl] The url is random: ${randomUrl}`, async (done) => {
    request(app)
      .post(`/create?url=${encodeURIComponent(randomUrl)}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.SUCCESS,
          data: {
            url: randomUrl,
          },
        });
        createdKey = res.body.data.key;
        done();
      });
  });
});

describe('Short Url Endpoints Step 2', () => {
  it(`[CreateShortUrl] The url is duplicate: ${randomUrl}`, async (done) => {
    request(app)
      .post(`/create?url=${encodeURIComponent(randomUrl)}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.SUCCESS,
          data: {
            url: randomUrl,
            key: createdKey,
          },
        });
        done();
      });
  });
  it(`[GetShortUrl] The short url is created`, async (done) => {
    request(app)
      .get(`/${createdKey}`)
      .expect(200)
      .end((err, res) => {
        if (err) return done(err);
        expect(res.body).toMatchObject({
          statusCode: StatusCode.SUCCESS,
          data: {
            url: randomUrl,
            key: createdKey,
          },
        });
        done();
      });
  });
});

afterAll(async (done) => {
  await getDbConnection().close();
  redisClient.quit(() => {
    done();
  });
});
