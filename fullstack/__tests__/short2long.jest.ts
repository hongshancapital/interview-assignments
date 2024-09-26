const axios = require('axios');

describe('short2long', () => {
  it('短链不存在', async () => {
    const result = await axios.get('http://localhost:3001/long-link/123')
    const { data } = result;
    expect(data).toStrictEqual({ code: 4, msg: '没有找到该sid对应的长链', body: '' });
  })

  it('短链存在,并能正常重定向', async () => {
    const result = await axios.get('http://localhost:3001/long-link/1i1gebn')
    const { data } = result;
    // console.log(data);
    expect(data).toMatch(/TypeScript 5.0 现已发布：全新的装饰器，速度、内存和包大小优化/)
  })
})