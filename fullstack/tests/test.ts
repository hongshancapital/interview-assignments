import request from "supertest";
import * as dotenv from "dotenv";
import {app} from "../src/index";
import {env, getCacheClient} from "../src/helper";
import ShortUrl from "../src/services/ShortUrl";
import {BaseConverter, encode} from "../src/transform";

dotenv.config();

const SHORT_URL_PREFIX = process.env.SHORT_URL_PREFIX as string

describe("Test Url", () => {
    const items: { url: any, status: boolean, short?: string }[] = [
        {url: "https://github.com", status: true},
        {url: "https://github.com/scdt-china/interview-assignments", status: true},
        {url: "https://github.com/scdt-china/interview-assignments?id=1", status: true},
        {url: "https://github.com/scdt-china/interview-assignments#id=1", status: true},
        {url: "https://github.com/scdt-china/interview-assignments?id=1#11", status: true},
        {url: "http://github.com", status: true},
        {url: "https://汉字.com", status: true},
        {url: "ftp://github.com", status: true},
        {url: "github.com", status: false},
        {url: "githubcom", status: false},
        {url: "https@//github.com", status: false},
        {url: "abc", status: false},
        {url: {test: 1}, status: false},
        {url: "", status: false},
        {url: "    ", status: false},
        {url: "汉字", status: false},
    ];
    items.forEach(async item => {
        it(`test create ${item.url}`, async () => {
            const response = await request(app).post("/api/shorturl").send({url: item.url}).set('Accept', 'application/json');
            expect(response.headers["content-type"]).toMatch(/json/);
            expect(response.body.status).toBe(item.status);
            if (item.status) {
                expect(() => {
                    if (typeof response.body.data.url !== "string") {
                        throw new Error("data.url type error")
                    }
                    const url = response.body.data.url as string
                    if (url.indexOf(SHORT_URL_PREFIX) !== 0) {
                        throw new Error("data.url prefix error")
                    }
                    const short = url.replace(SHORT_URL_PREFIX, "")
                    if (short === "") {
                        throw new Error("data.url short error")
                    }
                    item.short = short
                }).not.toThrow();
            }
        });
        it(`test get ${item.url}`, async () => {
            if (!item.status && item.short === undefined) {
                return expect(true).toBe(true)
            }
            const response = await request(app).get(`/api/shorturl/${item.short}`).set('Accept', 'application/json');
            expect(response.headers["content-type"]).toMatch(/json/);
            expect(response.body.status).toBe(item.status);
            expect(response.body.data.url).toEqual(item.url);
        });
    })
});

describe("Test get error", () => {
    const shorts = ["asc112", "啊", "_?", "langlanglanglanglanglang"]
    shorts.forEach(short => {
        it(`test get ${short}`, async () => {
            const response = await request(app).get(`/api/shorturl/${encodeURI(short)}`).set('Accept', 'application/json');
            expect(response.headers["content-type"]).toMatch(/json/);
            expect(response.body.status).toBe(false);
        });
    })
});

describe("Test other", () => {
    it("Test transform BaseConverter", () => {
        expect(() => BaseConverter.to(-1)).toThrow()
        expect(() => BaseConverter.from("测试")).toThrow()
    })

    it("Test helper env", () => {
        expect(env("test", "test")).toEqual("test")
    })

    it("Test transform encode", () => {
        expect(() => encode(Number.MAX_VALUE)).toThrow()
        expect(() => BaseConverter.from("测试")).toThrow()
    })

    it("Test database create", async () => {
        expect.assertions(1);
        try {
            await ShortUrl.create(`https://a.com/?a=` + "a".repeat(65535) + "b");
        } catch (e) {
            expect(e).toMatch('Create short url failed 02');
        }
    })

    it("Test database get data", async () => {
        // 确保数据库有id为234308的数据
        await (await getCacheClient()).del(ShortUrl.cacheKey(234308))
        expect((await ShortUrl.get(encode(234308))).id).toEqual(234308)
        expect((await ShortUrl.get(encode(234308))).id).toEqual(234308)
    })



    it("Test database get error", async () => {
        // 确保数据库没有id为1的数据
        await (await getCacheClient()).del(ShortUrl.cacheKey(1))
        expect.assertions(2);
        try {
            await ShortUrl.get(encode(1));
        } catch (e) {
            expect(e).toMatch('Invalid');
        }
        try {
            await ShortUrl.get(encode(1));
        } catch (e) {
            expect(e).toMatch('Invalid');
        }
    })
});
