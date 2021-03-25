/* eslint-disable @typescript-eslint/no-var-requires */
import request from "supertest";
import LevelDb from "../src/db/db";
import app from "../src/app";

const redis = require("redis");
const setMock = jest.fn((_key, value, cb) => cb(null, value));
const getMock = jest.fn((_key, cb) => cb(null, "get test url"));
const incrMock = jest.fn((_key, cb) => cb(null, 10));
redis.createClient = jest.fn(() => {
  return {
    set: setMock,
    get: getMock,
    incr: incrMock,
  };
});
import * as cache from "../src/db/cache";
cache.connectRedis({ port: 0 });



beforeEach(() => {
  jest.resetModules();
});

test("should return 302 Redirect", (done) => {
  jest.mock("../src/services/shorturl",()=>{
    return {
      getOriginUrlById: jest.fn(_key => Promise.resolve("http://test.com"))
    }
  });
  request(app)
    .get("/ab")
    .expect(302)
    .end(function (err, res) {
      if (err) done(err);
      expect(res.text).toContain("Found. Redirecting to");
      done();
    });
});

test("reset data should return 0", (done) => {
  LevelDb.setId = jest.fn(() => Promise.resolve(true));
  request(app)
  .get("/reset")
  .expect(200)
  .end(function (err, res) {
    expect(res.text).toBe("0");
    if (err) done(err);
    done();
  });
});

test("reset data should return error msg", (done) => {
  LevelDb.setId = jest.fn(() => Promise.reject('set error'));
  request(app)
  .get("/reset")
  .expect(200)
  .end(function (err, res) {
    expect(res.text).toBe("reset error");
    if (err) done(err);
    done();
  });
});