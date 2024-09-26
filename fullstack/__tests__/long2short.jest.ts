const axios = require('axios');

describe('long2short', () => {
  it('输入为空', async () => {
    const result = await axios.post('http://localhost:3001/short-link', { url: '' })
    const { data } = result;
    expect(data).toStrictEqual({ code: 1, msg: 'url不能为空', body: '' });
  })

  it('短链已存在', async () => {
    const result = await axios.post('http://localhost:3001/short-link', { url: 'https://mp.weixin.qq.com/s/vZF-pOWsJND5V9h8X3xVPA' })
    const { data } = result;
    expect(data).toStrictEqual({ code: 0, msg: '短链已存在', body: '1i1gebn' });
  })

  it('输入链接非法', async () => {
    const result = await axios.post('http://localhost:3001/short-link', { url: 'hts://1234' })
    const { data } = result;
    expect(data).toStrictEqual({ code: 2, msg: 'url不符合规范', body: '' });
  })
})