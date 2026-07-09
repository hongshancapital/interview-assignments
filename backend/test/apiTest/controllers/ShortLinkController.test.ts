import app from '../../../src/app';
import supertest from 'supertest';
import sinon, { SinonSandbox } from 'sinon';
import { mockRedis } from '../../mock/mockRedis';
import assert from 'assert';
import lruCache from '../../../src/cache/lruCache';
import RedisFactory from '../../../src/cache/RedisFactory';
import SeqTokenGenerator from '../../../src/tokenGenerators/SeqTokenGenerator';
import configs from '../../../src/configs';
import sequelize from '../../../src/models/config';

describe("request to ShortLinkController", function () {
  const request = supertest(app);
  let sandbox: SinonSandbox = sinon.createSandbox();
  let data = {
    originalUrl: "http://test.originalurl.com/user=joke",
    shortUrl: "",
  }

  this.beforeAll(() => {
    SeqTokenGenerator.reset();
    mockRedis(sandbox);
  })

  it("should success, the original url is valid", async function() {
    const response = await request.post('/api/v1/shortLinks/shortUrl').send({originalUrl: data.originalUrl});
    assert.equal(response.body.code, "B00000");
    data.shortUrl = response.body.data;
  })

  it("should shortUrl is same, retry to create shortUrl", async function() {
    const response = await request.post('/api/v1/shortLinks/shortUrl').send({originalUrl: data.originalUrl});
    assert.equal(response.body.code, "B00000");
    assert.equal(response.body.data, data.shortUrl)
  })

  it("should return B10003, no param originalUrl", async function() {
    const response = await request.post('/api/v1/shortLinks/shortUrl').send({});
    assert.equal(response.body.code, "B10003");
  })

  it("should return B10004, originalUrl length is longer than 4000", async function() {
    let originalUrl = "";
    for (let i = 0; i < 4001; i++) {
      originalUrl += i;
    }
    const response = await request.post('/api/v1/shortLinks/shortUrl').send({originalUrl});
    assert.equal(response.body.code, "B10004");
  })

  it("should return B10005, originalUrl is invalid", async function() {
    const response = await request.post('/api/v1/shortLinks/shortUrl').send({originalUrl: "xxxx.dddd.xxx"});
    assert.equal(response.body.code, "B10005");
  })

  it("should fetch original url successful", async function() {
    const response = await request.get('/api/v1/shortLinks/originalUrl').query({shortUrl: data.shortUrl});
    assert.equal(response.body.code, "B00000");
    assert.equal(response.body.data, data.originalUrl);
  })

  it("should return B10002, no param shortUrl", async function() {
    const response = await request.get('/api/v1/shortLinks/originalUrl').query({});
    assert.equal(response.body.code, "B10002");
  })

  it("should return B10001, no originalUrl", async function() {
    const response = await request.get('/api/v1/shortLinks/originalUrl').query({shortUrl: "http://xxxx.xxxx"});
    assert.equal(response.body.code, "B10001");
  })

  it("shortUrl stored in LRU", function() {
    const originalUrl = lruCache.get(data.shortUrl.replace(configs.shortLinktHost, ""));
    assert.equal(originalUrl, data.originalUrl);
  })

  it("shortUrl stored in redis", async function() {
    const originalUrl = await RedisFactory.getClient().get(`shortUrl:${data.shortUrl.replace(configs.shortLinktHost, "")}`);
    assert.equal(originalUrl, data.originalUrl);
  })

  this.afterAll(() => {
    sandbox.restore();
  })
})