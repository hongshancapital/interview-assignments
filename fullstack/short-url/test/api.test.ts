/* eslint-disable @typescript-eslint/no-var-requires */
import request from "supertest";
import LevelDb from "../src/db/db";
import app from "../src/app";
import { invalidURL, notFound } from "../src/const";

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

const mockTable:any = {batch:jest.fn(() => mockTable),put:jest.fn(() => mockTable),write:jest.fn(() => mockTable)}
LevelDb.urlTable = mockTable;
LevelDb.setId = jest.fn(() => Promise.resolve(true));
LevelDb.setUrl = jest.fn(() => Promise.resolve(true));
LevelDb.getCurrentId = jest.fn(() => Promise.resolve(1));
LevelDb.getOriginUrl = jest.fn(() => Promise.resolve("test url in db"));

beforeEach(() => {
  jest.resetModules();
});

describe("GET /shorturl", () => {
  it("should return invalid URL", (done) => {
    request(app)
      .get("/shorturl?url=/www.a.com")
      .expect(invalidURL)
      .end(function (err, _res) {
        if (err) done(err);
        done();
      });
  });

  it("should return 200 OK", (done) => {
    getMock.mockImplementation(
      jest.fn((_key, cb) => cb(null, ""))
    );
    request(app)
      .get("/shorturl?url=http://www.a.com")
      .expect(200)
      .end(function (err, res) {
        if (err) done(err);
        expect(res.text).toBe("K");
        done();
      });
  });
});

describe("GET /originurl", () => {
  
  it("should return url from cache", (done) => {
    getMock.mockImplementation(
      jest.fn((_key, cb) => cb(null, "test url in cache"))
    );
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
    getMock.mockImplementation(jest.fn((_key, cb) => cb(null, "")));
    request(app)
      .get("/originurl?key=CX")
      .expect(200)
      .end(function (err, res) {
        expect(res.text).toBe("test url in db");
        if (err) return done(err);
        return done();
      });
  });

  it("should return Not Found", (done) => {
    getMock.mockImplementation(jest.fn((_key, cb) => cb(null, "")));
    LevelDb.getOriginUrl = jest.fn(() => Promise.reject());
    request(app)
    .get("/originurl?key=A")
    .end(function (err, res) {
      if (err) done(err);
      expect(res.text).toBe(notFound);
      done();
    });
  });  

  it("should return 400 Bad Request", (done) => {
    getMock.mockImplementation(jest.fn((_key, cb) => cb(null, "")));
    LevelDb.getOriginUrl = jest.fn(() => Promise.reject("error"));
    request(app)
    .get("/originurl?key0=A")
    .expect(400)
    .end(function(err, _res){
      if (err) return done(err);
      return done();
    });
  });
});

describe("GET /404", () => {
  it("should return 404 Not Found", (done) => {
    request(app)
      .get("/404")
      .expect(404)
      .end(function (err, res) {
        if (err) done(err);
        expect(res.text).toBe(notFound);
        done();
      });
  });
});
