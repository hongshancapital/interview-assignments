import {describe, it, expect, afterAll, beforeAll} from '@jest/globals';
import request from 'supertest';
import routes from '../src/server/routes';
import express from "express";
import bodyParser from "body-parser";
import mongoose from "mongoose";
import redis from '../src/server/lib/redis'

const app = express();

// 注册前置
beforeAll(()=>{

    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: true }));
    app.use(routes);
})


describe('Shorten URL API', () => {

    it('should return short URL when long URL is provided', async () => {
        const res = await request(app)
            .post('/api/shortUrls')
            .send({ longUrl: 'https://www.google.com/'});
        expect(res.status).toEqual(200);
        expect(res.body).toHaveProperty('shortUrl');
    });

    it('should redirect to long URL when short URL is provided', async () => {
        const res1 = await request(app)
            .post('/api/shortUrls')
            .send({ longUrl: 'https://www.google.com/'});
        const res2 = await request(app).get(`/${res1.body.shortUrl}`);
        expect(res2.status).toEqual(302);
        expect(res2.header.location).toEqual('https://www.google.com/');
    });

    it('should return 404 when invalid short URL is provided', async () => {
        const res = await request(app).get('/invalid-short-url');
        expect(res.status).toEqual(404);
    });
});

afterAll(() => {
    mongoose.disconnect();
    redis.disconnect();
})
