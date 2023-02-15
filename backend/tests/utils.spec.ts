import {validateUrl, base10to62} from '../src/app/utils'
import {describe, expect, test} from '@jest/globals';

describe('utils', () => {
    describe('validateUrl', () => {
        it('should return false if url length exceeds 2048 characters', () => {
            let url = '0'.padStart(3000, '0');
            let ret = validateUrl(url);
            expect(ret).toBe(false);
        });
        it('should return false if url not contain protocol', () => {
            let url = 'test.com'
            let ret = validateUrl(url);
            expect(ret).toBe(false);
        });
        it('should return false if url protocol is not http[s]', () => {
            let url = 'ttt://192.168.3.3/';
            let ret = validateUrl(url);
            expect(ret).toBe(false);
        });
        it('should return false if url contains invalid characters', () => {
            let url = 'https://\\/index.php'
            let ret = validateUrl(url);
            expect(ret).toBe(false);
        });
        it('should return true if url is valid', () => {
            let url = 'https://3.2.4.53:1111/index.php?a=123#ddd';
            let ret = validateUrl(url);
            expect(ret).toBe(true);
        });
    });

    describe('decimalTo62', () => {
        it('should return 00000000 for first convert', () => {
            let num  = 0;
            let ret = base10to62(num);
            expect(ret).toBe('00000000');
        });
        it('should return 0000000a for 11th convert', () => {
            let num = 10;
            let ret = base10to62(num);
            expect(ret).toBe('0000000a');
        });

        it('should return 2g5pYsGV for number 8127398171231 convert', () => {
            let num = 8127398171231;
            let ret = base10to62(num);
            expect(ret).toBe('2g5pYsGV');
        });
    });
});