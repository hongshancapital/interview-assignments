import { compress, uncompress } from '../../src/utils/compress';
import { expect } from 'chai';

describe('Utils compress', () => {
  it('should return a string who s length is le then input', () => {
    const string1 = 'http://www.baidu.com';
    const string2 = '百度';
    const string3 =
      'https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%9F%AD%E9%93%BE%E6%8E%A5&fenlei=256&rsv_pq=0xb5ff137d0005a5b3&rsv_t=f542qq%2FP7tGICxWsKxDRS7lkP3FiGS9vJsHSVqaGSyz80VZQbNPqPpy2QxTf&rqlang=en&rsv_enter=1&rsv_dl=ib&rsv_sug3=14&rsv_sug1=12&rsv_sug7=100&rsv_sug2=0&rsv_btype=i&inputT=3329&rsv_sug4=3564';

    expect(compress(string1).length).lessThanOrEqual(string1.length);
    expect(compress(string2).length).lessThanOrEqual(string2.length);
    expect(compress(string3).length).lessThanOrEqual(string3.length);
  });
});

describe('Utils uncompress', () => {
  it('should return a string which is the same as the string before compression', () => {
    const string1 = 'http://www.baidu.com';
    const string2 = '百度';
    const string3 =
      'https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%9F%AD%E9%93%BE%E6%8E%A5&fenlei=256&rsv_pq=0xb5ff137d0005a5b3&rsv_t=f542qq%2FP7tGICxWsKxDRS7lkP3FiGS9vJsHSVqaGSyz80VZQbNPqPpy2QxTf&rqlang=en&rsv_enter=1&rsv_dl=ib&rsv_sug3=14&rsv_sug1=12&rsv_sug7=100&rsv_sug2=0&rsv_btype=i&inputT=3329&rsv_sug4=3564';

    expect(uncompress(compress(string1))).equal(string1);
    expect(uncompress(compress(string2))).equal(string2);
    expect(uncompress(compress(string3))).equal(string3);
  });
});
