import { expect } from "chai";
import * as supertest from "supertest";
import app from "../server";
let newShortUrl: string = "";
describe("短域名存储接口验证", function () {
  before(function () {
    console.log("开始测试存储接口");
  });

  it("短域名存储接口测试--01", async function () {
    const res = await supertest(app)
      .post("/saveLongUrl")
      .send({ longUrl: "http://123.xyz/234sdrf5fg/?a=12" });
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(true);
    newShortUrl = resJson.result.shortUrl;
  });

  it("短域名存储接口测试--02", async function () {
    const res = await supertest(app)
      .post("/saveLongUrl")
      .send({ longUrl: "123" });
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Invalid Url!");
  });

  it("短域名存储接口测试--03", async function () {
    const res = await supertest(app).post("/saveLongUrl").send({ longUrl: "" });
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Invalid Url!");
  });

  it("短域名存储接口测试--04", async function () {
    const res = await supertest(app).post("/saveLongUrl").send({});
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Invalid Url!");
  });
});

describe("短域名读取接口验证", function () {
  before(function () {
    console.log("开始测试读取接口");
  });

  it("短域名读取接口测试--01", async function () {
    const res = await supertest(app).get(
      `/fetchLongUrl?shortUrl=${newShortUrl}`
    );
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(true);
  });

  it("短域名读取接口测试--02", async function () {
    const res = await supertest(app).get(`/fetchLongUrl?shortUrl=${"122222"}`);
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Your short url not exists！");
  });

  it("短域名读取接口测试--03", async function () {
    const res = await supertest(app).get(`/fetchLongUrl?shortUrl=${""}`);
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Your short url not exists！");
  });

  it("短域名读取接口测试--04", async function () {
    const res = await supertest(app).get(`/fetchLongUrl`);
    expect(res.statusCode).to.equal(200);
    const resJson = JSON.parse(res.text);
    expect(resJson.success).to.equal(false);
    expect(resJson.msg).to.equal("Your short url not exists！");
  });
});
