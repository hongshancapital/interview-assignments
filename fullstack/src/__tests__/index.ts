import { ResUtil } from "../util/index";
import supertest from "supertest";
import { server } from "../app";

describe("API test", () => {
  test("get without key", async () => {
    await supertest(server)
      .get("/api/v1/get")
      .expect(ResUtil.showError({ msg: "未找到对应url" }));
  });

  test("direct get url expect undefined", async () => {
    const randomUrl = `${Math.random() * 10000}`;
    await supertest(server)
      .get("/api/v1/get")
      .query({
        key: randomUrl,
      })
      .expect(ResUtil.showError({ msg: "未找到对应url" }));
  });

  test("save without url", async () => {
    const randomUrl = `${Math.random() * 10000}`;
    let request = await supertest(server);
    await request
      .get("/api/v1/storage")
      .expect(ResUtil.showError({ msg: "未检测到url" }));
  });

  test("save url then get", async () => {
    const randomUrl = `${Math.random() * 10000}`;
    let request = await supertest(server);
    let res = await request.get("/api/v1/storage").query({
      longUrl: randomUrl,
    });
    let key = JSON.parse(res.text)?.data?.key;

    await request
      .get("/api/v1/get")
      .query({
        key: key,
      })
      .expect(ResUtil.showResult({ data: randomUrl }));
  });

  test("save same url then get", async () => {
    const randomUrl = `${Math.random() * 10000}`;
    let request = await supertest(server);
    let res = await request.get("/api/v1/storage").query({
      longUrl: randomUrl,
    });
    let key = JSON.parse(res.text)?.data?.key;

    // 二次存入相同url
    let res2 = await request.get("/api/v1/storage").query({
      longUrl: randomUrl,
    });
    let key2 = JSON.parse(res2.text)?.data?.key;
    // 期待返回的key值不变
    expect(key2).toBe(key);

    // 两个key均能正常取值
    await request
      .get("/api/v1/get")
      .query({
        key: key,
      })
      .expect(ResUtil.showResult({ data: randomUrl }));
  });
});
