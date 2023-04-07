import { ShortUrlError } from '../src/ShortUrlError';

describe('ShortUrlError', () => {
    it('constructor-with-msg', () => {
        const error = new ShortUrlError('测试错误！');
        expect(error.name).toBe('ShortUrlError');
    });
    it('constructor-with-no-msg', () => {
        const error = new ShortUrlError();
        expect(error.name).toBe('ShortUrlError');
        expect(error.message).toBe('业务错误');
        expect(error.stack).toBeDefined();
    });
});
