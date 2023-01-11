const axios = require('axios')
 
describe('url module tests', () => {
  beforeAll(async () => {})
  afterAll(async () => {})
 
  it('无效url转短链', async () => {
    const response = await axios.post('http://localhost:50501/url/shorten', {
      url: 'testtt',
    });
    expect(response.data.code).toEqual('A00001');
  });

  it('无效短链获取', async () => {
    const response = await axios.get('http://localhost:50501/url/restore/12345');
    expect(response.data.code).toEqual('A00001');
  });

  it('正常的url转短链', async () => {
    const response = await axios.post('http://localhost:50501/url/shorten', {
      url: 'https://www.baidu.com/s?ie=UTF-8&wd=hello%20world',
    });
    expect(response.data.data.shortUrl).toEqual('http://localhost:50501/xesspYebj');
  });

  it('正常短链获取', async () => {
    const response = await axios.get('http://localhost:50501/url/restore/xesspYebj', {
      url: 'testtt',
    });
    expect(response.data.data.url).toEqual('https://www.baidu.com/s?ie=UTF-8&wd=hello%20world');
  });

  it('短链长度校验', async () => {
    const response = await axios.post('http://localhost:50501/url/shorten', {
      url: 'https://www.baidu.com/s?ie=UTF-8&wd=hello%20world123',
    });
    expect(response.data.data.shortenCode.length).toEqual(8);
  });
})