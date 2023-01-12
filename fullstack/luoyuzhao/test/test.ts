
import 'mocha'
import Container from 'typedi';
import UrlEntity from '../src/model/UrlEntity';
import ApiService from "../src/service/ApiService";
import Strtool from "../src/tools/StrTool";


const assert = require('assert');
const chai = require("chai");
const service = Container.get(ApiService);
const expect = chai.expect
const should = chai.should()


describe('StrTools', function () {
  it('MD5-test', function () {
    let str = Strtool.getMd5("http://www.baidu.com")
    console.log("md5-value:" + str);
    assert.equal(typeof (str), 'string');
  });
});

describe('StrTools', function () {
  it('Max-convert-test', function () {
    let str = Strtool.convert64Base(BigInt(999999999999999999))
    console.log("short-code:" + str);
    assert.equal(str.length, 8);
  });
});

describe('StrTools', function () {
  it('min-convert-test', function () {
    let str = Strtool.convert64Base(BigInt(10))
    console.log("short-code:" + str);
    assert.equal(str.length, 1);
  });
});


describe('ApiService', function () {
  it('long2short', function () {
    let long = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    service.long2short(long).then(short => {
      expect(short.length<long.length).to.be.ok;
    });
  });
  it('short2long', function () {
    service.short2long("mJaC").then(long => {
      expect(long&&long.length > 4).to.be.ok;
    });
  });
});




describe('UrlEntity', function () {
  it('valid', function () {
    let entity=new UrlEntity('aaaaa/bvbbbb/ccccc',10000000000);
    assert(entity.md5.length>0);
    assert(entity.short.length<8);
  });
});


