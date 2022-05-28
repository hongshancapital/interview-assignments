import {should,use,request} from "chai";
import chaiHttp from "chai-http";

const apiUrl = "http://127.0.0.1:3000";
should();
use(chaiHttp);

const testUrl = 'http://baidu.com/'+ new Date();
let testUrlId: string;

describe("短域名模块", () => {
    describe("POST /api/shortUrl", () => {
        it("短域名存储接口", (done) => {
            request(apiUrl)
                .post("/api/shortUrl")
                .send({url: testUrl})
                .end((error, response) => {
                    response.should.have.status(200);
                    response.body.should.be.a("object");
                    response.body.should.have.property("urlId");
                    testUrlId = response.body.urlId;
                    done();
                });
        });
        it("重复调用短域名存储接口，参数相同", (done) => {
            request(apiUrl)
                .post("/api/shortUrl")
                .send({url: testUrl})
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
            request(apiUrl)
                .get("/api/shortUrl/"+testUrlId)
                .end((error, response) => {
                    response.should.have.status(200);
                    response.body.should.be.a("object");
                    response.body.should.have.property("url").eq(testUrl);
                    done();
                });
        });
        it("短域名读取接口，入参错误", (done) => {
            request(apiUrl)
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