const request = require('supertest');
import app from './../main';
import {describe,it,expect} from '@jest/globals';
describe("add short key",()=>{

    it('add with no url', async () => {
        const response = await request(app).post('/api/shorten')
            .set('Accept', 'application/json')
            .expect('Content-Type', "application/json; charset=utf-8");
        expect(response.body.message).toEqual('invalid url');
    });

    it('add invalid url', async () => {
        const response = await request(app).post('/api/shorten')
            .set('Accept', 'application/json')
            .send({"url": "//:www.baidu.com"})
            .expect('Content-Type', "application/json; charset=utf-8");
        console.log("message is:%j",response.body.message);
        expect(response.body.message).toEqual('invalid url');
    });

    it('add  legal and not used  url ', async () => {
        const response = await request(app).post('/api/shorten')
            .set('Accept', 'application/json')
            .send({"url": "http://www.jd.com"})
            .expect('Content-Type', "application/json; charset=utf-8");
        expect(response.body.shortId).not.toBeNull();
    });

    it('add  used  url', async () => {
        const response = await request(app).post('/api/shorten')
            .set('Accept', 'application/json')
            .send({"url": "http://www.jd.com"})
            .expect('Content-Type', "application/json; charset=utf-8");
        expect(response.body.shortId).not.toBeNull();
    });

    it('add legal  url when cachePool no keys', async () => {
        const response = await request(app).post('/api/shorten')
            .set('Accept', 'application/json')
            .send({"url": "http://www.fox.com"})
            .expect('Content-Type', "application/json; charset=utf-8");
        expect(response.body.message).toEqual('Server is  busy');
    });
});

describe("get long url",()=>{
    it('get  no url', async () => {
        const response = await request(app).get('/key/')
            .set('Accept', 'application/json');
        expect(response.body.message).toEqual(undefined);
    });

    it('get  no url', async () => {
        const response = await request(app).get('/key/sdsfsfwfefs')
            .set('Accept', 'application/json');
        expect(response.body.message).toEqual("invalid key");
    });

    it('get right url', async () => {
        const response = await request(app).get('/key/B5VM6vf')
            .set('Accept', 'application/json').
            expect(200);
        expect(response.body.url).toEqual("http://www.sina.com/34242");
    });
    it('get wrong url', async () => {
        const response = await request(app).get('/key/kjiw')
            .set('Accept', 'application/json');
        expect(response.body.message).toEqual('not found');
    });
});