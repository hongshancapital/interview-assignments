import ShortUrlService from '../../services/ShortUrlService'

describe('ShortUrlService', () => {
    describe('validateUrl', () => {
        let url: string;
        let returnValue: boolean;
        it('should return false if url length exceeds 2048 characters', () => {
            url = '0'.padStart(3000, '0');
            returnValue = ShortUrlService.validateUrl(url);
            expect(returnValue).toBe(false);
        });
        it('should return false if url not contain protocol', () => {
            url = 'test.com'
            returnValue = ShortUrlService.validateUrl(url);
            expect(returnValue).toBe(false);
        });
        it('should return false if url protocol is not http[s]', () => {
            url = 'ttt://192.168.3.3/';
            returnValue = ShortUrlService.validateUrl(url);
            expect(returnValue).toBe(false);
        });
        it('should return false if url contains invalid characters', () => {
            url = 'https://\\/index.php'
            returnValue = ShortUrlService.validateUrl(url);
            expect(returnValue).toBe(false);
        });
        it('should return true if url is valid', () => {
            url = 'https://3.2.4.53:1111/index.php?a=123#ddd';
            returnValue = ShortUrlService.validateUrl(url);
            expect(returnValue).toBe(true);
        });
    });

    describe('decimalTo62', () => {
        let number: number;
        let returnValue: string;
        it('should return 00000000 for first convert', () => {
            number = 0;
            returnValue = ShortUrlService.decimalTo62(number);
            expect(returnValue).toBe('00000000');
        });
        it('should return 0000000a for 11th convert', () => {
            number = 10;
            returnValue = ShortUrlService.decimalTo62(number);
            expect(returnValue).toBe('0000000a');
        });

        it('should return 2g5pYsGV for number 8127398171231 convert', () => {
            number = 8127398171231;
            returnValue = ShortUrlService.decimalTo62(number);
            expect(returnValue).toBe('2g5pYsGV');
        });
    });
});
