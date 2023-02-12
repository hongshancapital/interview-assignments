import shortUrlService from '../../service/shortUrlService';
import redis from '../../infrastructure/redis';

const regExp = /^https?\:\/\/.*/i;

test('getShortUrl when exist the short url test', async () => {
    await redis.connect();
    const result = await shortUrlService.getShortUrl('http://testUrl.com')
    expect(result).toEqual('http://41');
    await redis.disConnect();
});

test('getShortUrl when not exist the short url test', async () => {
    await redis.connect();
    const result = await shortUrlService.getShortUrl('http://'+Math.random().toString(36).slice(8)+'.com');
    const isMatch = regExp.test(result);
    expect(isMatch).toBe(true);
    await redis.disConnect();
});

test('getOriginUrl test',async () => {
    await redis.connect();
    const result = await shortUrlService.getOriginUrl('http://41');
    expect(result).toEqual('http://testUrl.com');
    await redis.disConnect();
});

