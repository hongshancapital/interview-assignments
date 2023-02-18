// let request = require("supertest");
// const assert = require("assert");

import Request from "supertest";
import assert from 'assert';
import app from "../src/server/app";

let request = Request(app);

describe("GET /api/long/to/short", () => {
    it("should return shortLink OK", async () => {
        let oriUrl = 'https://github.com/base62/base62.js';
        return await request.get(`/api/long/to/short?oriUrl=${oriUrl}`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 0)
            });
    });

    it("create other shortLink OK", async () => {
        let oriUrl = 'https://www.bilibili.com/video/BV1EZ4y1i7fu?spm_id_from=333.999.list.card_archive.click';
        return await request.get(`/api/long/to/short?oriUrl=${oriUrl}`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 0)
            });
    });

    it("oriUrl is null", async () => {
        return await request.get(`/api/long/to/short`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 1)
            });
    });

    it("oriUrl is not url", async () => {
        let oriUrl = 'hasdasdasdads';
        return await request.get(`/api/long/to/short?oriUrl=${oriUrl}`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 1)
            });
    });
});

describe("GET /api/short/to/long", () => {
    it("should return longLink OK", async () => {
        let shortUrl = 'http://qingaoti.cn/3';
        return await request.get(`/api/short/to/long?shortUrl=${shortUrl}`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 0)
            });
    });

    it("shortUrl is null", async () => {
        return await request.get(`/api/short/to/long`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 1)
            });
    });

    it("shortUrl is not url ", async () => {
        let shortUrl = 'hdsadasd';
        return await request.get(`/api/short/to/long?shortUrl=${shortUrl}`)
            .expect("Content-Type", /json/)
            .expect(200)
            .then( (res)=> {
                assert(res.body.code === 1)
            });
    });
});


