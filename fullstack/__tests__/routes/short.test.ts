import mongoose from "mongoose";
import config from "config";
import request from "supertest";
import app from "../../src/app";

beforeEach(async () => {
    await mongoose.connect(config.get('db.mongoURI'));
});

describe("POST /short/gen", () => {
    it("should gen a short url", async () => {
        const res = await request(app).post("/short/gen").send({
            longUrl: "www.baidu.com",
        });
        expect(res.statusCode).toBe(409);
    });
});

describe("POST /short/gen", () => {
    it("should gen a short url", async () => {
        const res = await request(app).post("/short/gen").send({
            longUrl: "http://www.baidu.com",
        });
        expect(res.statusCode).toBe(200);
        expect(res.body.data.urlCode).toBe("CB62Vlo6");
    });
});

describe("POST /short/gen", () => {
    it("should gen a short url", async () => {
        const res = await request(app).post("/short/gen").send({
            longUrl: `http://www.baidu.com?${new Date().getTime()}`,
        });
        expect(res.statusCode).toBe(200);
    });
});

describe("GET /short/urlCode/:urlCode", () => {
    it("should get a long url", async () => {
        const res = await request(app).get(
            `/short/urlCode/baidu`
        );
        expect(res.statusCode).toBe(200);
        expect(res.body.data.longUrl).toBe("www.baidu.com");
    });
});

describe("GET /short/urlCode/:urlCode", () => {
    it("should not get a long url", async () => {
        const res = await request(app).get(
            `/short/urlCode/baiduxxx`
        );
        expect(res.statusCode).toBe(409);
    });
});

describe("POST /short/gen", () => {
    it("should not gen a short url  with Error", async () => {
        await mongoose.connection.close();
        const res = await request(app).post("/short/gen").send({
            longUrl: "http://www.baidu.com",
        });
        expect(res.statusCode).toBe(409);
    });
});

describe("GET /short/urlCode/:urlCode", () => {
    it("should not get a long url with Error", async () => {
        await mongoose.connection.close();
        const res = await request(app).get(
            `/short/urlCode/baiduxxx`
        );
        expect(res.statusCode).toBe(409);
    });
});

afterEach(async () => {
    await mongoose.connection.close();
});
