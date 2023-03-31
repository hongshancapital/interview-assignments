import request from 'supertest';
import app from "../../src/main";

let port = 60001;

describe("Main Test", () => {
    let server: any;
    beforeEach(async () => {
        server = await app.listen(port);
    });

    afterEach(async () => {
        await server.close();
    });

    test('GET / | should get status code with 200', (done) => {
        request(server)
            .get("/")
            .expect("Content-Type", 'text/html; charset=utf-8')
            .expect(200, done);
    });
});
