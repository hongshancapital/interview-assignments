import axios from 'axios';

const server = 'http://localhost:3000';
const longUrl = 'http://www.baidu.com';
const shortUrl = 'su.co/G4qmZ';
const longUrl2 = 'http://www.baidu.com2';
const shortUrl2 = 'su.co/G4qmZ2';

const generateShortUrl = async (lurl: string) => {
  return await axios
    .get(`${server}/generateShortUrl?longUrl=${lurl}`)
    .then((response) => response.data)
    .catch((error) => {
      if (error?.response?.data?.message) {
        console.error(error);
        throw new Error('[API] ' + error.response.data.message);
      }
      throw error;
    });
};

const transferToLongUrl = async (surl: string) => {
  return await axios
    .get(`${server}/transferToLongUrl?shortUrl=${surl}`)
    .then((response) => response.data)
    .catch((error) => {
      if (error?.response?.data?.message) {
        console.error(error);
        throw new Error('[API] ' + error.response.data.message);
      }
      throw error;
    });
};

test('生成短链接接口-已存在记录', async () => {
  const res = await generateShortUrl(longUrl);
  expect(res).toBe(shortUrl);
});

test('生成短链接接口-新生成记录', async () => {
  const res = await generateShortUrl(longUrl2);
  expect(res.length).toBeLessThan(13);
});

test('解析短链接至长链接', async () => {
  const res = await transferToLongUrl(shortUrl);
  expect(res).toBe(longUrl);
});

test('解析短链接至长链接-不存在', async () => {
  const res = await transferToLongUrl(shortUrl2);
  expect(res).toBe('Not Found');
});
