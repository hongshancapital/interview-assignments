import { expect } from "chai";
import { HostDomain, myUtil } from "../utils/myUtil";
describe("测试数字转Base62字符串", function () {
  it("numToBase62String(12345678)返回", function () {
    expect(myUtil.numToBase62String(12345678)).to.equal("PNFQ");
  });
  it("numToBase62String(952456312123)返回", function () {
    expect(myUtil.numToBase62String(952456312123)).to.equal("gLEdCuD");
  });
});

describe("测试Base62字符串转数字", function () {
  it("base62StringToNumber('PNFQ')返回12345678", function () {
    expect(myUtil.base62StringToNumber("PNFQ")).to.equal(12345678);
  });
  it("base62StringToNumber('gLEdCuD')返回952456312123", function () {
    expect(myUtil.base62StringToNumber("gLEdCuD")).to.equal(952456312123);
  });
});

describe("测试生成短链接的函数", function () {
  it("genShortUrl(87846181248)返回短域名", function () {
    expect(myUtil.genShortUrl(87846181248))
      .to.includes(HostDomain)
      .with.length.gt(HostDomain.length);
  });
});

describe("测试校验网址的函数", function () {
  before(function () {
    console.log("开始测试校验网址的函数");
  });
  it("校验http://123.com/11122?dfsdfds", function () {
    expect(myUtil.validateUrl("http://123.com/11122?dfsdfds")).equal(true);
  });
  it("校验123.com/11122?dfsdfds", function () {
    expect(myUtil.validateUrl("http://123.com/11122?dfsdfds")).equal(true);
  });
  it("校验123.com", function () {
    expect(myUtil.validateUrl("123.com")).equal(true);
  });
  it("校验abc//:abc.com", function () {
    expect(myUtil.validateUrl(":abc.com")).equal(false);
  });
  it("校验//:abc.com", function () {
    expect(myUtil.validateUrl(":abc.com")).equal(false);
  });
  it("校验:abc.com", function () {
    expect(myUtil.validateUrl(":abc.com")).equal(false);
  });
  it("校验abc", function () {
    expect(myUtil.validateUrl("abc")).equal(false);
  });
  it("校验空字符串", function () {
    expect(myUtil.validateUrl("")).equal(false);
  });
});
