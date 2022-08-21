import { createShortUrl, loadShortUrl } from '../src/shortUrlServices';
import { storeInstance } from '../src/store';
import { hashUrl } from '../src/hash';

describe('createShortUrl test', () => {
    it('http://www.baidu.com', () => {
        let long = 'http://www.baidu.com';
        let short = createShortUrl(long);
        expect(short).toBe('3kb2uL');
        let dup = createShortUrl(long);
        expect(dup).toBe('3kb2uL');
    })

    it('http://www.g.cn', () => {
        let long = 'http://www.g.cn';
        let hash = hashUrl(long)
        storeInstance.save(hash, 'http://www.baidu.com');
        let short = createShortUrl(long);
        expect(short).toBe('3x8YFQ');
        let dup = createShortUrl(long);
        expect(dup).toBe('3x8YFQ');
    })


    it('3kb2uL', () => {
        let short = '3kb2uL';
        let long = loadShortUrl(short);
        expect(long).toBe('http://www.baidu.com');
        let from_load = loadShortUrl(short);
        expect(from_load).toBe(long);
    })


    it('https://www.google.com', () => {
        let long = 'https://www.google.com';
        let hash = hashUrl(long)
        storeInstance.save(hash, 'http://www.baidu.com');
        let short = createShortUrl(long);
        expect(short).not.toBe(hash);
        let from_load = loadShortUrl(short);
        expect(from_load).toBe(long);
    })

})