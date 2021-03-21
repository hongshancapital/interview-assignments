import request from "supertest";
import LevelDb from "../src/db/db";
import app from "../src/app";
const redis = require("redis");
const setMock = jest.fn((key, value, cb) => cb(null, value));
const getMock = jest.fn((key, cb) => cb(null, "get test url"));
const incrMock = jest.fn((key, cb) => cb(null, 10));
redis.createClient = jest.fn(() => {
  return {
    set: setMock,
    get: getMock,
    incr: incrMock,
  };
});

import * as cache from "../src/db/cache";
cache.connectRedis({ port: 0 });

const mockCache = () => {
  return {
    __esModule: true,
    // default: jest.fn(() => 42),
    connectRedis: jest.fn(() => Promise.resolve()),
    getNewId: jest.fn(() => Promise.resolve(10)),
    get: jest.fn(() => Promise.resolve("test url in cache")),
    set: jest.fn(() => Promise.resolve(true)),
  };
};

LevelDb.setId = jest.fn(() => Promise.resolve(true));
LevelDb.setUrl = jest.fn(() => Promise.resolve(true));
LevelDb.getCurrentId = jest.fn(() => Promise.resolve(1));
LevelDb.getOriginUrl = jest.fn(() => Promise.resolve("test url in db"));

beforeEach(() => {
  jest.resetModules();
});

describe("GET /shorturl", () => {
  it("should return 200 OK", (done) => {
    request(app)
      .get("/shorturl?url=http://www.a.com")
      .expect(200)
      .end(function (err, res) {
        if (err) done(err);
        expect(res.text).toBe("K");
        done();
      });
  });

  it("should return invalid URL", () => {
    request(app).get("/shorturl?url=/www.a.com").expect("Invalid URL");
  });
});

describe("GET /originurl", () => {
  getMock.mockImplementation(
    jest.fn((key, cb) => cb(null, "test url in cache"))
  );
  it("should return url from cache", (done) => {
    request(app)
      .get("/originurl?key=CX")
      .expect(200)
      .end(function (err, res) {
        expect(res.text).toBe("test url in cache");
        if (err) return done(err);
        return done();
      });
  });

  it("should return url from db", (done) => {
    getMock.mockImplementation(jest.fn((key, cb) => cb(null, "")));
    request(app)
      .get("/originurl?key=CX")
      .expect(200)
      .end(function (err, res) {
        expect(res.text).toBe("test url in db");
        if (err) return done(err);
        return done();
      });
  });

  it("should return Not Found", () => {
    getMock.mockImplementation(jest.fn((key, cb) => cb(null, "")));
    LevelDb.getOriginUrl = jest.fn(() => Promise.reject());
    request(app).get("/originurl?key=A").expect("Not Found");
  });
});
