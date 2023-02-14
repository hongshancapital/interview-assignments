import shortUrlService from '../../service/shortUrlService';
import redis from '../../infrastructure/redis';
import '../../infrastructure/mysql';

const testUrl:string = 'http://'+Math.random().toString(36).slice(8)+'.com';
let shortUrl:string;
const regExp = /^https?\:\/\/.*/i;

test('getShortUrl when not exist the short url test', async () => {
    await redis.connect();
    const result = await shortUrlService.getShortUrl(testUrl);
    const isMatch = regExp.test(result);
    expect(isMatch).toBe(true);
    shortUrl = result;
    await redis.disConnect();
});

test('getShortUrl when exist the short url test', async () => {
    await redis.connect();
    const result = await shortUrlService.getShortUrl(testUrl)
    expect(result).toEqual(shortUrl);
    await redis.disConnect();
});


test('getOriginUrl test',async () => {
    await redis.connect();
    const result = await shortUrlService.getOriginUrl(shortUrl);
    expect(result).toEqual(testUrl);
    await redis.disConnect();
});

