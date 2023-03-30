const request = require('supertest');
const app = require("../../src/main");
let port = 60002;

describe("Uri Domain Test", () => {
    let server;
    beforeEach(async () => {
        server = await app.listen(port);
    });

    afterEach(async () => {
        await server.close();
    });

    test('POST /uri | should create uri with status code 200', (done) => {
        request(server)
            .post("/uri")
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(200, done);
    });
});
