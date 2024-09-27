import "reflect-metadata";

import urlController from "../../../src/controllers/url.controller";
import urlService from "../../../src/services/url.service";
import redisService from "../../../src/services/redis.service";
import statisticsService from "../../../src/services/statistics.service";

import { ApplicationError } from "../../../src/helpers/application.err";
import { Statistics } from "../../../src/interfaces/statistics.interface";
import { urlEncode } from "../../../src/utils/transfer";

jest.mock('../../../src/services/mongo.service');
jest.mock('../../../src/services/redis.service');
jest.mock('../../../src/services/url.service');
jest.mock('../../../src/services/statistics.service');

describe("Controllers: UrlController", () => {
  const user = {
    uid: '12121',
    username: '12121',
  };

  afterEach(() => {
    jest.resetAllMocks();
  });

  describe("create", () => {
    it("should return short url if original url already exists", async () => {
      const url = "https://www.google.com";
      const code = "abc123";
      const req: any = { body: { url }, user };
      const res: any = { json: jest.fn() };
      const next: any = jest.fn();

      jest.spyOn(redisService, "get").mockResolvedValue(code);

      await urlController.create(req, res, next);

      const safeUlr = urlEncode(url);
      expect(redisService.get).toBeCalledWith(`url:origin:${user.uid}:${safeUlr}`);
      expect(res.json).toBeCalledWith({ url: `http://127.0.0.1:3000/${code}` });
    });

    it("should return short url if original url does not exist", async () => {
      const url = "https://www.google.com";
      const code = "abc123";
      const req: any = { body: { url }, user };
      const res: any = { json: jest.fn() };
      const next: any = jest.fn();

      jest.spyOn(urlService, "findByOption").mockResolvedValue(null);
      jest.spyOn(urlService, "createByOption").mockResolvedValue({ code } as any);
      jest.spyOn(redisService, "setEx").mockResolvedValue("OK");

      await urlController.create(req, res, next);
      const safeUlr = urlEncode(url);

      expect(urlService.findByOption).toBeCalledWith(safeUlr, user.uid);
      expect(urlService.createByOption).toBeCalledWith(safeUlr, user.uid);
      expect(redisService.setEx).toBeCalledWith(`url:origin:${user.uid}:${safeUlr}`, code);
      expect(res.json).toBeCalledWith({ url: `http://127.0.0.1:3000/${code}` });
    });

    it("should throw error if url is not provided", async () => {
      const req: any = { body: {}, user};
      const res: any = { json: jest.fn() };
      const next: any = jest.fn();

      await urlController.create(req, res, next);

      expect(next).toBeCalledWith(new ApplicationError(400, "Invalid URL"));
    });

    it("should throw error if url is invalid", async () => {
      const req: any = { body: { url: 'Invalid URL' }, user };
      const res: any = { json: jest.fn() };
      const next: any = jest.fn();
      await urlController.create(req, res, next);

      expect(next).toBeCalledWith(new ApplicationError(400, "Invalid URL"));
    });
  });

  describe("redirect", () => {
    it("should redirect to original url if code exists in Redis cache", async () => {
      const req: any = { params: { code: "abc123" }, header: jest.fn(), ip: '127.0.0.1' };
      const res: any = { redirect: jest.fn() };
      const next: any = jest.fn();
      const mockOriginUrl = {
        url: "http://example.com",
        code: 'abc123',
        uid: '1231231',
      };
      jest.spyOn(redisService, "bloomExists").mockResolvedValue(true);
      jest.spyOn(redisService, "get").mockResolvedValue(JSON.stringify(mockOriginUrl));

      await urlController.redirect(req, res, next);

      expect(redisService.get).toHaveBeenCalledWith("url:code:abc123");
      expect(res.redirect).toHaveBeenCalledWith(302, mockOriginUrl.url);
      expect(next).not.toHaveBeenCalled();
    });

    it("should redirect to original url if code exists in database", async () => {
      const req: any = { params: { code: "abc123"  }, header: jest.fn(), ip: '127.0.0.1' };
      const res: any = { redirect: jest.fn() };
      const next: any = jest.fn();
      const mockOriginUrl = {
        url:  "http://example.com",
        code: 'abc123',
        uid: '1231231',
      };
      jest.spyOn(redisService, "bloomExists").mockResolvedValue(true);
      jest.spyOn(redisService, "get").mockResolvedValue(null);
      jest.spyOn(redisService, "setEx").mockResolvedValue(null);
      jest.spyOn(urlService, "findByCode").mockResolvedValue(mockOriginUrl as any);
      jest.spyOn(statisticsService, "createByOption").mockResolvedValue({} as Statistics);

      await urlController.redirect(req, res, next);

      expect(redisService.get).toHaveBeenCalledWith("url:code:abc123");
      expect(urlService.findByCode).toHaveBeenCalledWith("abc123");
      expect(res.redirect).toHaveBeenCalledWith(302, mockOriginUrl.url);
      expect(next).not.toHaveBeenCalled();
    });

    it("should throw ApplicationError with 404 status code if code does not exist", async () => {
      const req: any = { params: { code: "abc123" } };
      const res: any = { redirect: jest.fn() };
      const next: any = jest.fn();
      jest.spyOn(redisService, "bloomExists").mockResolvedValue(true);
      jest.spyOn(redisService, "get").mockResolvedValue(null);
      jest.spyOn(urlService, "findByCode").mockResolvedValue(null);

      await urlController.redirect(req, res, next);

      expect(redisService.get).toHaveBeenCalledWith("url:code:abc123");
      expect(urlService.findByCode).toHaveBeenCalledWith("abc123");
      expect(next).toHaveBeenCalledWith(new ApplicationError(404, "URL not existed"));
      expect(res.redirect).not.toHaveBeenCalled();
    });

    it("should throw ApplicationError with 400 status code if code is invalid", async () => {
      const req: any = { params: { code: "invalid_code" } };
      const res: any = { redirect: jest.fn() };
      const next: any = jest.fn();

      await urlController.redirect(req, res, next);

      expect(redisService.get).not.toHaveBeenCalled();
      expect(urlService.findByCode).not.toHaveBeenCalled();
      expect(next).toHaveBeenCalledWith(new ApplicationError(400, "Invalid Code"));
      expect(res.redirect).not.toHaveBeenCalled();
    });

    it("should throw ApplicationError with 400 status code if code is not in bloom", async () => {
      const req: any = { params: { code: "23232" } };
      const res: any = { redirect: jest.fn() };
      const next: any = jest.fn();

      jest.spyOn(redisService, "bloomExists").mockResolvedValue(false);

      await urlController.redirect(req, res, next);

      expect(redisService.get).not.toHaveBeenCalled();
      expect(urlService.findByCode).not.toHaveBeenCalled();
      expect(next).toHaveBeenCalledWith(new ApplicationError(404, "URL not existed"));
      expect(res.redirect).not.toHaveBeenCalled();
    });
  });

});
