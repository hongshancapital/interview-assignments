import { should, use, request } from "chai";
import chaiHttp from "chai-http";
import { ShortId } from "../utils/id";

const shortIdObj = new ShortId(19);
should();
use(chaiHttp);

const apiHost = "http://localhost:8000";

// utils 下 id 生成器测试
describe("ShortId", () => {
  describe("#generateShortId()", () => {
    it("assert shortId less than or equal 8", () => {
      expect(
        shortIdObj.generateShortId("https://google.com?q=ts").length
      ).toBeLessThanOrEqual(8);
    });
  });
});

// 接口测试
describe("routes", () => {
  describe("slink.ts", () => {
    describe("POST /slink", () => {
      request(apiHost)
        .post("/slink")
        .send({ app_id: "123", lurl: "https://www.baidu.com?search=Java2" })
        .end((error, response) => {
          response.should.have.status(200);
          response.body.should.have.property("code").eq(0);
        });
    });

    describe("GET /:shortId", () => {
      request(apiHost)
        .post("/P773NlRz")
        .end((error, response) => {
          response.should.have.status(302);
          response.should.have.header("Location");
        });
    });
  });
});
