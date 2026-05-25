import { assert, expect } from "chai";
const axios = require("axios");
// const api = require('../src/api'); // 引入要测试的API模块

describe("api:/url/save", () => {
  it("post request: /url/save", async () => {
    const data = {
      url: "/scdt-china/interview-assignments/blob/master/fullstack/README.md",
    };
    const res = await axios.post("http://localhost:8888/url/save", data);
    expect(res.status).to.equal(200);
    expect(res.data.ok).to.equal(true);
    assert.isString(res.data.data.url);
    assert.match(res.data.data.url, /^[0-9a-zA-Z]{1,8}$/);
  });
});
