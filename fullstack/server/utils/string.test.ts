import 'mocha';
import { expect } from 'chai';
import { getBytes } from './string';

describe('string 工具函数测试', function () {
  it('empty string should return 0', function () {
    const count = getBytes('');
    console.log('count =>', count);
    expect(count).to.be.equal(0);
  })

  it('English should return 26', function () {
    const count = getBytes('abcdefghigklmnopqrstuvwsyz');
    console.log('count =>', count);
    expect(count).to.be.equal(26);
  })

  it('Chinese chractor should return 8', function () {
    const count = getBytes('热爱祖国');
    console.log('count =>', count);
    expect(count).to.be.equal(8);
  })

  it('Chinese and English chractor should return 16', function () {
    const count = getBytes('我的英文名是Karl');
    console.log('count =>', count);
    expect(count).to.be.equal(16);
  })

  it('test url should return 82', function () {
    const count = getBytes('https://www.taobao.com/shop/shopid=123456789?oldshop=true&hasdog=false#goodJob张三');
    console.log('count =>', count);
    expect(count).to.be.equal(82);
  })
})

