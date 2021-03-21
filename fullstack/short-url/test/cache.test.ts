const redis = require("redis");
const setMock = jest.fn((key, value, cb) => cb(null, value));
const getMock = jest.fn((key, cb) => cb(null, "get test url"));
const incrMock = jest.fn((key, cb) => cb(null, 15));
redis.createClient = jest.fn(() => {
  return {
    set: setMock,
    get: getMock,
    incr: incrMock,
  };
});
import * as cache from "../src/db/cache";
cache.connectRedis({port:0,host:'host'});


test("init should return input value", (done) => {
  cache.initCache(1234).then((data) => {
    expect(data).toBe(1234);
    done();
  });
});

test("get should return get test url", (done) => {
  cache.get("a").then((data) => {
    expect(data).toBe("get test url");
    done();
  });
});

test("set should return set test url", (done) => {
  cache.set("a", "set test url").then((data) => {
    expect(data).toBe("set test url");
    done();
  });
});

test("getNewId should return 15", (done) => {
  cache.getNewId().then((data) => {
    expect(data).toBe(15);
    done();
  });
});

test("get should return error", (done) => {
  getMock.mockImplementation((key: string, cb: any) => cb("error", null));
  cache.get("a").catch((error) => {
    expect(error).toBe("error");
    done();
  });
});

test("set should return error", (done) => {
  setMock.mockImplementation((key: string, value, cb: any) =>
    cb("error", null)
  );
  cache.set("a", "url").catch((error) => {
    expect(error).toBe("error");
    done();
  });
});

test("getNewId should return error", (done) => {
  incrMock.mockImplementation((key, cb: any) => cb("error", null));
  cache.getNewId().catch((error) => {
    expect(error).toBe("error");
    done();
  });
});
