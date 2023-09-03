import { assert, expect } from "chai";
const axios = require("axios");

describe("api:/url/get", () => {
  let shortUrl: string = "";

  before(() => {
    // 先存一个长url
    return axios
      .post("http://localhost:8888/url/save", {
        url: "/scdt-china/interview-assignments/blob/master/fullstack/README.md",
      })
      .then(
        (
          res: ApiResult = { status: 0, data: { ok: false, data: { url: "" } } }
        ) => {
          shortUrl = res.data.data.url;
        }
      );
  });

  describe("/url/get", () => {
    it("post request: /url/get", async () => {
      const data = {
        url: shortUrl,
      };
      const res: ApiResult = await axios.post(
        "http://localhost:8888/url/get",
        data
      );
      expect(res.status)?.to.equal(200);
      expect(res.data.ok)?.to.equal(true);
      assert.isString(res.data.data.url);
    });
  });
});

interface UrlData {
  url: string;
}

interface ApiResultData {
  ok: boolean;
  data: UrlData;
}

interface ApiResult {
  status: number;
  data: ApiResultData;
}
