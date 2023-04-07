/**
 * short url 相关业务上的错误
 */
export class ShortUrlError extends Error {
    constructor(message = '业务错误') {
        super(message);
        this.name = 'ShortUrlError';
    }
}
