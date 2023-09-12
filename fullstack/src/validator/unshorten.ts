import { SHORT_URL_LENGTH } from '../core/core';

function validate(code: string): Promise<string> {
    return new Promise((resolve, reject) => {
        if (isValidShortUrl(code)) {
            resolve(code);
        } else {
            reject({'status': 400, 'msg': 'Please input a valid short url.'});
        }
    });
}

function isValidShortUrl(code: string) {
    if (!code) {
        return false;
    }
    if (code.length != SHORT_URL_LENGTH) {
        return false;
    }
    for (let i = 0; i < code.length; i++) {
        let c: string = code.charAt(i);
        if (c != '_' && c != '-' && !(c >= '0' && c <= 'z')) {
            return false;
        }
    }
    return true;
}

export { validate, isValidShortUrl };