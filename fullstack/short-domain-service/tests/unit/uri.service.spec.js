const uriService = require('../../src/uri/service')

describe("Uri Serice Test", () => {
    let converter = uriService.convert10To62;

    test('convert10To62 | should get 0 with input 0', () => {
        const base = 0;
        const result = converter(base);
        expect(result).toBe(String(0));
    });
    test('convert10To62 | should get 1 with input 1', () => {
        const base = 1;
        const result = converter(base);
        expect(result).toBe(String(1));
    });
    test('convert10To62 | should get null when input is not a number', () => {
        const base = 'hello';
        const result = converter(base);
        expect(result).toBe(null);
    });
    test('convert10To62 | should get a valid string value when input is 1,000', () => {
        const base = 1000;
        const result = converter(base);
        expect(result).toBe('g8');
    });
    test('convert10To62 | should get a valid string value when input is 1,000,000', () => {
        const base = 1000000;
        const result = converter(base);
        expect(result).toBe('4c92');
    });
    test('convert10To62 | should get a valid string value when input is 1000,000,000', () => {
        const base = 1000000000;
        const result = converter(base);
        expect(result).toBe('15FTGg');
    });
    test('convert10To62 | should get a valid string value when input is 1,000,000,000,000', () => {
        const base = 1000000000000;
        const result = converter(base);
        expect(result).toBe('hBxM5A4');
    });
    test('convert10To62 | should get null when input is 1,000,000,000,000,000 that exceed 8 bit string', () => {
        const base = 1000000000000000;
        const result = converter(base);
        expect(result).toBe(null);
    });
});
