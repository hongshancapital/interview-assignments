import * as storage from '../storage/url_storage'

const URL_CHARS = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-';
export const SHORT_URL_PREFIX = 'http://go/';
export const SHORT_URL_LENGTH = 8;
export const UNKNOW_SHORT_URL_TEXT = 'Unknown short URL: ';

export function shortenUrl(longUrl: string): string {
    const id: number = storage.getOrInsertFromLongUrl(longUrl);
    let shortUrl = _decimalToBase64(id);

    while (shortUrl.length < SHORT_URL_LENGTH) {
        shortUrl = '0' + shortUrl;
    }
    shortUrl = shortUrl.slice(0, SHORT_URL_LENGTH)    
    return SHORT_URL_PREFIX + shortUrl;
}

export function getLongUrl(shortUrl: string): string {
    const postfix = shortUrl.slice(-1*SHORT_URL_LENGTH);
    const id = _base64ToDecimal(postfix);
    if (storage.hasId(id)) {
        return storage.getLongUrlFromId(id);
    } else {
        throw new Error("Unknown short URL: " + shortUrl);
    }
}

function _decimalToBase64(num: number): string {
    let chars = '';
    while (num > 0) {
        chars = URL_CHARS.charAt(num % URL_CHARS.length) + chars;
        num = Math.floor(num / URL_CHARS.length);
    }
    return chars;
}

function _base64ToDecimal(chars: string): number {
    let i: number;
    let id = 0;
    let base = 1;
    
    for (i = chars.length - 1; i >= 0; i--) {
        id += URL_CHARS.indexOf(chars.charAt(i)) * base;
        base *= URL_CHARS.length;
    }
    return id;
}
