import { createApp, HooksApplication } from "@midwayjs/hooks-testing-library";
import { get, set } from "../apis/lambda/shortlink";
import { ceil, isIncluded } from "../utils/helper";

type IResultObject = {
  url: string;
  shortUrl: string;
};

/**
 * 单测：短链读写接口
 */
describe("test:api.shortlink.get", () => {
  let app: HooksApplication;
  const params = {
    shortUrl: "https://xinz.cn/u/qiaaia",
  };

  beforeAll(async () => {
    app = await createApp();
  });

  afterAll(async () => {
    await app.close();
  });

  it("runFunction:shortlink.get", async () => {
    const result = await app.runFunction(get, params);

    expect(result).toHaveProperty("data");
    expect(result.data[0]).not.toBeUndefined();
    expect(result.data[0]).toMatchObject(params);

    const hasError = await app.runFunction(get, {});
    expect(hasError).toHaveProperty("code");
    expect(hasError.code).toBeLessThan(0);
  });

  it("request:shortlink.get", async () => {
    const response = await app.request(get, params).expect(200);
    const result = response.body;

    expect(result).toHaveProperty("data");
    expect(result.data[0]).not.toBeUndefined();
    expect(result.data[0]).toMatchObject(params);
  });

  it("runFunction:shortlink.set", async () => {
    const params = {
      url: "https://news.sina.com.cn/c/xl/2022-05-08/doc-imcwiwst6290215.shtml",
    };
    const result = await app.runFunction(set, params);

    expect(result).toHaveProperty("data");
    expect(result.data).toMatchObject(params);
    expect(result.data).toHaveProperty("shortUrl");

    const hasError = await app.runFunction(set, {});
    expect(hasError).toHaveProperty("code");
    expect(hasError.code).toBeLessThan(0);
  });
});

/**
 * 单测：公共函数库-向上取整
 */
describe("test:utils.functions.ceil", () => {
  it("runFunction", () => {
    expect(ceil(5 / 2.1)).toBe(3);
  });
});

/**
 * 单测：公共函数库-子集
 */
describe("test:utils.functions.isIncluded", () => {
  it("runFunction", () => {
    expect(isIncluded(["xinz", "qian"], ["xinz"])).toBe(true);
    expect(isIncluded(["xinz", "qian"], ["imxinz"])).toBe(false);
  });
});
