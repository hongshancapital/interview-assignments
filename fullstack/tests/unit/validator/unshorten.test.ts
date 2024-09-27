import { validate, isValidShortUrl} from '../../../src/validator/unshorten';

describe('Unit tests of <validator.unshorten>', () => {
    describe('test validate', () => {
        test('when input is valid short url.', async () => {
            expect(validate('hellowod')).resolves.toBe('hellowod');
        });
        test('when input is not a valid short url.', async () => {
            expect(validate('@#')).rejects.toStrictEqual({'status': 400, 'msg': 'Please input a valid short url.'});
        });
    });
    
    describe('test validate', () => {
        test('when input is valid short url.', async () => {
            expect(isValidShortUrl('0_Aa-9Zz')).toBeTruthy();
        });
        test('when input is empty.', async () => {
            expect(isValidShortUrl('')).toBeFalsy();
        });
        test('when input is too short.', async () => {
            expect(isValidShortUrl('123')).toBeFalsy();
        });
        test('when input is too long.', async () => {
            expect(isValidShortUrl('123456789')).toBeFalsy();
        });
        test('when input contains invalid character.', async () => {
            expect(isValidShortUrl('123+5678')).toBeFalsy();
        });
    });
});