import assert from 'assert';

import { ShortenService } from '../src/shortenService';

const urls = [{
  longUrl: 'www.baidu.com/xxx123xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxlllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkk',
  shortUrl: 'CS7xS4'
}]  

describe('ShortenService test suits', ()=> {
    const shortenService = new ShortenService();
    
    it('base62', async ()=> {
        const ret = shortenService.base62(1122442)
        assert.equal(ret, 'U0I5')
    })
    
    it('genShortUrl', async ()=> {
        const { longUrl, shortUrl } = urls[0];
        const ret = await shortenService.genShortUrl(longUrl);
        assert.equal(ret, shortUrl);
    })
})