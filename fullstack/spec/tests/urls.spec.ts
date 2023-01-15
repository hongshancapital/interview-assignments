import supertest, {SuperTest, Test, Response} from "supertest";
import logger from "jet-logger";
import app from "../../src/server";
import urlRepo from "../../src/repos/url-repo";
import urlRoutes from "../../src/routes/url-routes";
import HttpStatusCodes from "../../src/declarations/major/HttpStatusCodes";
import {urlNotFoundErr} from "../../src/services/url-service";

// **** Variables **** //

// Misc
const {paths} = urlRoutes,
  urlsPath = "/api" + paths.basePath,
  getShortUrlPath = `${urlsPath}${paths.getShortUrl}`,
  getOriginalUrlPath = `${urlsPath}${paths.getOriginalUrl}`;

// Dummy urls for GET req
const dummyShortUrl = {
  id: 821052863351,
  shortUrl: "cmxAye6O",
  urlHash: "7EC07C6A73ED45872AC354374343A9A2",
  originalUrl: "https://www.jianshu.com/p/4f03f3a1f1fdbb",
};

// Dummy originalUrl
const dummyOriginalUrlData = {
  originalUrl: "https://www.jianshu.com/p/4f03f3a1f1fdbb",
} as const;

// Dummy shortUrl
const dummyShortUrlData = {
  shortUrl: "cmxAye6O",
} as const;

// Test messages
const msgs = {
  getShortUrlSuccess:
    "should return a JSON object with a short url and a " 
    + `status code of "${HttpStatusCodes.OK}" if the request was successful.`,
  getShortUrlBad:
    "should return a JSON object containing an error message " +
    `and a status code of "${HttpStatusCodes.BAD_REQUEST}" if the request ` +
    "was unsuccessful.",
  getOriginalUrlSuccess:
    "should return a JSON object with a original url and a " 
    + `status code of "${HttpStatusCodes.OK}" if the request was successful.`,
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
  beforeAll((done) => {
    agent = supertest.agent(app);
    done();
  });
  // Test get shortUrl
  describe(`"POST:${getShortUrlPath}"`, () => {
    // Consts
    const callApi = (reqBody: TReqBody) => {
      return agent.post(getShortUrlPath).type("form").send(reqBody);
    };
    it(msgs.getShortUrlSuccess, (done) => {
      const ret = Promise.resolve(dummyShortUrl);
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      spyOn(urlRepo, "getOne").and.returnValue(ret);
      // Call API
      callApi(dummyOriginalUrlData).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.OK);
        // Caste instance-objects to 'Url' objects
        const respUrl = res.body.shortUrl;
        expect(respUrl).toEqual(dummyShortUrl.shortUrl);
        expect(res.body.error).toBeUndefined();
        done();
      });
    });

    // Get short url bad
    it(msgs.getShortUrlBad, (done) => {
      const errMsg = "One or more of the required params was missing or invalid.";
      spyOn(urlRepo, "getOne").and.throwError(errMsg);
      // Call API
      callApi({}).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
        expect(res.body.error).toBe(errMsg);
        done();
      });
    });

    // Get short url bad
    it(msgs.getShortUrlBad, (done) => {
      const errMsg = "Url is invalid!";
      spyOn(urlRepo, "getOne").and.throwError(errMsg);
      // Call API
      callApi({originalUrl: "aaahttps://www.jianshu.com/p/4f03f3a1f1fdbb"}).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
        expect(res.body.error).toBe(errMsg);
        done();
      });
    });
  });

  // Test get originalUrl
  describe(`"POST:${getOriginalUrlPath}"`, () => {
    // Consts
    const callApi = (reqBody: TReqBody) => {
      return agent.post(getOriginalUrlPath).type("json").send(reqBody);
    };
    it(msgs.getOriginalUrlSuccess, (done) => {
      const ret = Promise.resolve(dummyShortUrl);
      spyOn(urlRepo, "getOne").and.returnValue(ret);
      // Call API
      callApi(dummyShortUrlData).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.OK);
        // Caste instance-objects to 'Url' objects
        const respUrl = res.body.originalUrl;
        expect(respUrl).toEqual(dummyShortUrl.originalUrl);
        expect(res.body.error).toBeUndefined();
        done();
      });
    });

    // Get originalUrl bad params
    it(msgs.getOriginalUrlBad, (done) => {
      const errMsg = "One or more of the required params was missing or invalid.";
      spyOn(urlRepo, "getOne").and.throwError(errMsg);
      // Call API
      callApi({}).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
        expect(res.body.error).toBe(errMsg);
        done();
      });
    });

    // Get originalUrl bad params
    it(msgs.getOriginalUrlBad, (done) => {
      const errMsg = "Url is invalid!";
      spyOn(urlRepo, "getOne").and.throwError(errMsg);
      // Call API
      callApi({shortUrl: "Lr85qIoNaaaa"}).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.BAD_REQUEST);
        expect(res.body.error).toBe(errMsg);
        done();
      });
    });

    // Get originalUrl not found error
    it(msgs.getOriginalUrlNotFound, (done) => {
      callApi({shortUrl: "Lr85qIoN"}).end((err: Error, res: Response) => {
        !!err && logger.err(err);
        expect(res.status).toBe(HttpStatusCodes.NOT_FOUND);
        expect(res.body.error).toBe(urlNotFoundErr);
        done();
      });
    });
  });
});
