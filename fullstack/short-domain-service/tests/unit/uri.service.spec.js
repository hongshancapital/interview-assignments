const uriService = require('../../src/uri/service')

describe("Uri Service Test | ", () => {
    describe("convert10To62 Test | ", () => {
        let converter = uriService.convert10To62;
        test('should get 0 with input 0', () => {
            const base = 0;
            const result = converter(base);
            expect(result).toBe(String(0));
        });
        test('should get 1 with input 1', () => {
            const base = 1;
            const result = converter(base);
            expect(result).toBe(String(1));
        });
        test('should get null when input is not a number', () => {
            const base = 'hello';
            const result = converter(base);
            expect(result).toBe(null);
        });
        test('should get a valid string value when input is 1,000', () => {
            const base = 1000;
            const result = converter(base);
            expect(result).toBe('g8');
        });
        test('  should get a valid string value when input is 1,000,000', () => {
            const base = 1000000;
            const result = converter(base);
            expect(result).toBe('4c92');
        });
        test('should get a valid string value when input is 1000,000,000', () => {
            const base = 1000000000;
            const result = converter(base);
            expect(result).toBe('15FTGg');
        });
        test('should get a valid string value when input is 1,000,000,000,000', () => {
            const base = 1000000000000;
            const result = converter(base);
            expect(result).toBe('hBxM5A4');
        });
        test('should get null when input is 1,000,000,000,000,000 that exceed 8 bit string', () => {
            const base = 1000000000000000;
            const result = converter(base);
            expect(result).toBe(null);
        });
    });
    describe('getShortUri Test | ', () => {
        let getShortUri = uriService.getShortUri;
        test('should get short uri with host and key', () => {
            const host = 'localhost';
            const key = '6LAzd'
            const result = getShortUri(host, key);
            expect(result).toBe('localhost/uri/6LAzd');
        });
    })
    describe('saveUri and getUri Test | ', () => {
        beforeAll(() => {
            uriService.connectToRedis();
        })
        let saveUri = uriService.saveUri;
        let getUri = uriService.getUri;
        test('should save uri relation in redis', async () => {
            const key = '6LAzd'
            const longUri = 'www.yansong.fun'
            saveUri(key, longUri);
            const result = await getUri(key);
            expect(result).toBe('www.yansong.fun');
        });
        test('should get undefined when key is not exist', async () => {
            const key = 'Anything'
            const result = await getUri(key);
            expect(result).toBe(undefined);
        });
    })
})

