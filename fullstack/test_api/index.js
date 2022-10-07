"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const chai_1 = require("chai");
const chai_http_1 = __importDefault(require("chai-http"));
const apiUrl = "http://127.0.0.1:3000";
(0, chai_1.should)();
(0, chai_1.use)(chai_http_1.default);
const testUrl = 'http://baidu.com/' + new Date();
let testUrlId;
describe("短域名模块", () => {
    describe("POST /api/shortUrl", () => {
        it("短域名存储接口", (done) => {
            (0, chai_1.request)(apiUrl)
                .post("/api/shortUrl")
                .send({ url: testUrl })
                .end((error, response) => {
                response.should.have.status(200);
                response.body.should.be.a("object");
                response.body.should.have.property("urlId");
                testUrlId = response.body.urlId;
                done();
            });
        });
        it("重复调用短域名存储接口，参数相同", (done) => {
            (0, chai_1.request)(apiUrl)
                .post("/api/shortUrl")
                .send({ url: testUrl })
                .end((error, response) => {
                response.should.have.status(200);
                response.body.should.be.a("object");
                response.body.should.have.property("urlId").eq(testUrlId);
                done();
            });
        });
    });
    describe("GET /api/shortUrl", () => {
        it("短域名读取接口", (done) => {
            (0, chai_1.request)(apiUrl)
                .get("/api/shortUrl/" + testUrlId)
                .end((error, response) => {
                response.should.have.status(200);
                response.body.should.be.a("object");
                response.body.should.have.property("url").eq(testUrl);
                done();
            });
        });
        it("短域名读取接口，入参错误", (done) => {
            (0, chai_1.request)(apiUrl)
                .get("/api/shortUrl/000-001")
                .end((error, response) => {
                response.should.have.status(200);
                response.body.should.be.a("object");
                response.body.should.have.property("error").eq('invalid params: urlId');
                done();
            });
        });
    });
});
//# sourceMappingURL=index.js.map