import { validate, isValidLength, isValidUrl} from '../../../src/validator/shorten';

describe('Unit tests of <validator.shorten>', () => {
    describe('test validate', () => {
        test('when input is valid url.', async () => {
            expect(validate('https://baidu.com')).resolves.toBe('https://baidu.com');
        });
        test('when input is empty.', async () => {
            expect(validate('')).rejects.toStrictEqual({'status': 400, 'msg': 'Please input a url.'});
        });
        test('when input is too long.', async () => {
            const url: string = 'a'.repeat(2049);
            expect(validate(url)).rejects.toStrictEqual({'status': 400, 'msg': 'Url exceeds max size 2048.'});
        });
        test('when input is not valid url.', async () => {
            expect(validate('hello')).rejects.toStrictEqual({'status': 400, 'msg': 'Please input a valid url.'});
        });
    });
    
    describe('test isValidLength', () => {
        test('when url is valid.', async () => {
            expect(isValidLength('https://baidu.com')).toBeTruthy();
        });
        test('isValidLength with too long string', async () => {
            const url: string = 'a'.repeat(2049);
            expect(isValidLength(url)).toBeFalsy();
        });
    });

    describe('test isValidUrl', () => {
        test('when input is valid url.', async () => {
            expect(isValidUrl('https://baidu.com')).toBeTruthy();
        });
        test('when input is not valid url.', async () => {
            expect(isValidUrl('world')).toBeFalsy();
        });
    });
});