import supertest, { SuperTest, Test, Response } from "supertest";
import app from "../../src/server";
import urlRoutes from "../../src/routes/url-routes";
import HttpStatusCodes from "../../src/declarations/major/HttpStatusCodes";
import { urlNotFoundErr } from "../../src/services/url-service";
import { AppDataSource } from "../../src/data-source";
import { Url } from "../../src/entity/Url";
import request from "supertest";
// **** Variables **** //

// Misc
const { paths } = urlRoutes,
  urlsPath = "/api" + paths.basePath,
  getShortUrlPath = `${urlsPath}${paths.getShortUrl}`,
  getOriginalUrlPath = `${urlsPath}${paths.getOriginalUrl}`;

// Dummy url for GET req
const dummyShortUrl = Object.assign(new Url(), {
  id: 1,
  shortUrl: "kfk38qz1",
  urlHash: "E81C1F5749545C5F7D247B3A100FFE62",
  originalUrl: "https://www.baidu.com/",
});

// Dummy originalUrl
const dummyOriginalUrlData = {
  originalUrl: "https://www.baidu.com/",
} as const;

const dummyOriginalUrlDataNew = {
  originalUrl: "https://www.baidu.com/new",
} as const;

// Dummy shortUrl
const dummyShortUrlData = {
  shortUrl: "kfk38qz1",
} as const;

// Test messages
const msgs = {
  getShortUrlSuccess:
    "should return a JSON object with a short url and a " +
    `status code of "${HttpStatusCodes.OK}" if the request was successful.`,
  getShortUrlBad:
    "should return a JSON object containing an error message " +
    `and a status code of "${HttpStatusCodes.BAD_REQUEST}" if the request ` +
    "was unsuccessful.",
  getOriginalUrlSuccess:
    "should return a JSON object with a original url and a " +
    `status code of "${HttpStatusCodes.OK}" if the request was successful.`,
  getOriginalUrlBad:
    "should return a JSON object containing an error message " +
    `and a status code of "${HttpStatusCodes.BAD_REQUEST}" if the request ` +
    "was unsuccessful.",
  getOriginalUrlNotFound:
    "should return a JSON object with the error message " +
    `of "${urlNotFoundErr}" and a status code of ` +
    `"${HttpStatusCodes.NOT_FOUND}" if the original url was not found.`,
} as const;

// **** Types **** //

type TReqBody = string | object | undefined;

// **** Tests **** //

describe("url-router", () => {
  let agent: SuperTest<Test>;
  // Run before all tests
  beforeAll(async () => {
    await AppDataSource.initialize();
    agent = supertest.agent(app);
  });
  // Test get shortUrl
  describe(`"POST:${getShortUrlPath}"`, () => {
    it(msgs.getShortUrlSuccess, async () => {
      const res = await request(app)
        .post(getShortUrlPath)
        .send(dummyOriginalUrlData);
      expect(res.status).toBe(HttpStatusCodes.OK);
      const respUrl = res.body.shortUrl;
      expect(respUrl).toEqual(dummyShortUrl.shortUrl);
      expect(res.body.error).toBeUndefined();
    });
    // Get short url
    it(msgs.getShortUrlSuccess, async () => {
      const res = await request(app)
        .post(getShortUrlPath)
        .send(dummyOriginalUrlDataNew);
      expect(res.status).toBe(HttpStatusCodes.OK);
      const respUrl = res.body.shortUrl;
      expect(respUrl).toBeTruthy()
      expect(res.body.error).toBeUndefined();
    });

    // Get short url bad
    it(msgs.getShortUrlBad, async () => {
      const errMsg =
        "One or more of the required params was missing or invalid.";
      // Call API
      const res = await request(app).post(getShortUrlPath).send({});
      expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
      expect(res.body.error).toBe(errMsg);
    });

    // Get short url bad
    it(msgs.getShortUrlBad, async () => {
      const errMsg = "Url is invalid!";
      const res = await request(app)
        .post(getShortUrlPath)
        .send({ originalUrl: "aaahttps://www.jianshu.com/p/4f03f3a1f1fdbb" });
      expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
      expect(res.body.error).toBe(errMsg);
    });
  });

  // Test get originalUrl
  describe(`"POST:${getOriginalUrlPath}"`, () => {
    it(msgs.getOriginalUrlSuccess, async () => {
      const res = await request(app)
        .post(getOriginalUrlPath)
        .send(dummyShortUrlData);
      expect(res.status).toBe(HttpStatusCodes.OK);
      const respUrl = res.body.originalUrl;
      expect(respUrl).toEqual(dummyShortUrl.originalUrl);
      expect(res.body.error).toBeUndefined();
    });

    // Get originalUrl bad params
    it(msgs.getOriginalUrlBad, async () => {
      const errMsg =
        "One or more of the required params was missing or invalid.";
      const res = await request(app).post(getOriginalUrlPath).send({});
      expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
      expect(res.body.error).toBe(errMsg);
    });

    // Get originalUrl bad params
    it(msgs.getOriginalUrlBad, async () => {
      const errMsg = "Url is invalid!";
      const res = await request(app)
        .post(getOriginalUrlPath)
        .send({ shortUrl: "Lr85qIoNaaaa" });
      expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
      expect(res.body.error).toBe(errMsg);
    });

    // Get originalUrl not found error
    it(msgs.getOriginalUrlBad, async () => {
      const res = await request(app)
        .post(getOriginalUrlPath)
        .send({ shortUrl: "Lr85qIoN" });
      expect(res.status).toBe(HttpStatusCodes.NOT_FOUND);
      expect(res.body.error).toBe(urlNotFoundErr);
    });
  });
});
