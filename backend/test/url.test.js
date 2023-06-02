"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const supertest_1 = __importDefault(require("supertest"));
const fetch = (0, supertest_1.default)('http://127.0.0.1:3005');
describe('GET /user', function () {
    it('responds with json', function (done) {
        fetch.post('/shorten')
            .send({ url: "https://github.com/mdcruz/supertest-demo" })
            .expect(200, done);
    });
});
//# sourceMappingURL=url.test.js.map