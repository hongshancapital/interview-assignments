import { generateShortCode, validateShortCodeAndGetIdIfTrue } from '../service/codeUtil';

describe('generateShortCode', () => {
    it('should generate correct short code', () => {
        for (let i = 0; i < 100; i++) {
            const id = Math.floor(Math.random() * 1000000);
            const shortCode = generateShortCode(id);
            const {
                isValid
            } = validateShortCodeAndGetIdIfTrue(shortCode);
            expect(isValid).toBe(true);
            const id2 = validateShortCodeAndGetIdIfTrue(shortCode).id;
            expect(id2).toBe(id);
        }
    });
});

describe('validateShortCodeAndGetIdIfTrue', () => {
    it('returns false for an invalid short code', () => {
        const shortCode = 'invalid';
        const result = validateShortCodeAndGetIdIfTrue(shortCode);
        expect(result.isValid).toBe(false);
        expect(result.id).toBe(undefined);
    });

    it('returns the ID for a valid short code', () => {
        const id = 123456;
        const shortCode = generateShortCode(id);
        const result = validateShortCodeAndGetIdIfTrue(shortCode);
        expect(result.isValid).toBe(true);
        expect(result.id).toBe(id);
    });

    it('returns undefined ID for an invalid short code', () => {
        const shortCode = 'invalid';
        const result = validateShortCodeAndGetIdIfTrue(shortCode);
        expect(result.isValid).toBe(false);
        expect(result.id).toBe(undefined);
    });

    it('returns undefined ID for an short code with unsupported character', () => {
        const shortCode = '*invalid';
        const result = validateShortCodeAndGetIdIfTrue(shortCode);
        expect(result.isValid).toBe(false);
        expect(result.id).toBe(undefined);
    });

});
