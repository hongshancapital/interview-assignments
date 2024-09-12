import {describe, expect, test} from '@jest/globals';
import Util from "../util/Util";

describe("random str",()=>{
    test('random random length', () => {
        expect(Util.generateShortId().length).toBe(8);
    });
});

describe("url legality",()=>{
    test('legal url', () => {
        expect(Util.isValidUrl("http://www.baidu.com")).toBe(true);
    });
    test('legal url', () => {
        expect(Util.isValidUrl("https://www.baidu.com")).toBe(true);
    });

    test('illegal url', () => {
        expect(Util.isValidUrl("https://www.BAIDU.com/AFJAIA/WR2R;SFA")).toBe(false);
    });

    test('illegal url', () => {
        expect(Util.isValidUrl("www.baidu.com")).toBe(false);
    });

    test('illegal url', () => {
        expect(Util.isValidUrl("http:/www.baidu.com")).toBe(false);
    });
});

describe("callback test",()=>{

    it('catch exception', async () => {
        const mockFn = jest.fn().mockRejectedValueOnce(new Error('test error'));
        const listener = Util.defineListener(mockFn);

        await expect(listener()).resolves.toBe(undefined);
        expect(mockFn).toHaveBeenCalled();
    });

    it('agrs is ok ', async () => {
        const mockFn = jest.fn().mockResolvedValueOnce('test result');
        const listener = Util.defineListener(mockFn);

        await expect(listener(1, 2, 3)).resolves.toBe(undefined);
        expect(mockFn).toHaveBeenCalledWith(1, 2, 3);
    });
});