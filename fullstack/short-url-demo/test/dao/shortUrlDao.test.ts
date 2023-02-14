import db from '../../dao/shortUrlDao';

const shortUrl = Math.random().toString(36).slice(8);
const originUrl = Math.random().toString(36).slice(10);
test('saveShortUrl test', async () => {
    const result:boolean = await db.saveShortUrl(shortUrl, originUrl);
    expect(result).toBe(true);
});

test('queryShortUrl test', async () => {
    const result:string = await db.queryShortUrl(originUrl);
    expect(result).toEqual(shortUrl);

});

test('queryOriginUrl test',async () => {
    const result:string = await db.queryOriginUrl(shortUrl);
    expect(result).toEqual(originUrl);
});