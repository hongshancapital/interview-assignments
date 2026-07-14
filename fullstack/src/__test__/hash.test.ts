import { hashUrl, hash, checkHashLength } from '../src/hash'

describe('Hash', () => {
    it('hashUrl', () => {
        expect(hashUrl('http://www.google.com')).toBe('2gSW1b');
        expect(hash('hello')).toBe(613153351)
        expect(hash('hello', 9007199254740991)).toBe(595297739)
    })
    it('hash too long', () => {
        expect(() => { checkHashLength('123456789') }).toThrow('hash too long');
        expect(checkHashLength('12345678')).toBe(true);
    })
})