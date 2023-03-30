const request = require('supertest');
const app = require("../../src/main");
const { MockRedisClient } = require("../mock/redis-mock");
let port = 60002;

describe("Uri Domain Test", () => {
    let server;
    beforeEach(async () => {
        server = await app.listen(port);
    });

    afterEach(async () => {
        await server.close();
    });

    test('POST /uri | should create short uri with response status code 200', (done) => {
        request(server)
            .post("/uri")
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(200, done);
    });

    test('POST /uri | should create uri with long uri as request body and get short uri as response', (done) => {
        // mock redis database size;
        jest
            .spyOn(MockRedisClient.prototype, 'sendCommand')
            .mockImplementation(() => 99999999);
        request(server)
            .post("/uri")
            .send({ uri: 'www.yansong.fun' })
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(/uri\/6LAzd/, done);
    });
});
