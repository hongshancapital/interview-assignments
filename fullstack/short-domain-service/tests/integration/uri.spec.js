const request = require('supertest');
const app = require("../../src/main");
const { MockRedisClient } = require("../mock/redis-mock");
let port = 60002;

describe("Uri Domain Test ｜ ", () => {
    let server;
    beforeEach(async () => {
        server = await app.listen(port);
    });
    afterEach(async () => {
        jest.resetAllMocks();
        await server.close();
    });

    test('POST /uri | should create short uri with response status code 200', (done) => {
        request(server)
            .post("/uri")
            .send({ uri: 'http://www.yansong.fun' })
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(200, done);
    });
    test('POST /uri | should not create short uri when request without uri info', (done) => {
        request(server)
            .post("/uri")
            .expect(400, done);
    });
    test('POST /uri | should create uri with long uri as request body and get short uri as response', (done) => {
        // mock redis database size;
        jest
            .spyOn(MockRedisClient.prototype, 'sendCommand')
            .mockImplementation(() => 99999999);
        request(server)
            .post("/uri")
            .send({ uri: 'http://www.yansong.fun' })
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(/uri\/6LAzd/, done);
    });
    test('POST /uri | should get 400 when uri is not valid uri', (done) => {
        request(server)
            .post("/uri")
            .send({ uri: 'www.yansong.fun' })
            .expect(400, done);
    });
    test('Get /uri/:key | should get long uri with redirect', (done) => {
        // mock origin uri
        jest
            .spyOn(MockRedisClient.prototype, 'get')
            .mockImplementation(() => 'http://www.yansong.fun');
        const mockShortUriKey = '6LAzd';
        request(server)
            .get(`/uri/${mockShortUriKey}`)
            .expect(301)
            .expect('Moved Permanently. Redirecting to http://www.yansong.fun', done);
    });
    test('Get /uri/:key | should not 400 when short key not exist', (done) => {
        // mock origin uri
        jest
            .spyOn(MockRedisClient.prototype, 'get')
            .mockImplementation(() => undefined);
        const mockShortUriKey = 'anything';
        request(server)
            .get(`/uri/${mockShortUriKey}`)
            .expect(400, done)
    });
    test('should create uri and keep in redis database', async () => {
        jest
            .spyOn(MockRedisClient.prototype, 'sendCommand')
            .mockImplementation(() => 99999999);
        // create short uri
        const response = await request(server)
            .post("/uri")
            .send({ uri: 'http://www.outdoorlife.cc' })
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(/uri\/6LAzd/);
        // get origin uri info
        return await request(server)
            .get(`/uri/6LAzd`)
            .expect(301)
            .expect('Moved Permanently. Redirecting to http://www.outdoorlife.cc');
    });
    
});
