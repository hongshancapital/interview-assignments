import crypto from 'crypto';

const SHORT_URL_LENGTH = 8;

function generateHash(input: string): string {
    const md5 = crypto.createHash('md5');
    md5.update(input);
    const hash = md5.digest('base64url');

    return hash;
}

function pickFirst(hash: string): string {
    return hash.substring(0, SHORT_URL_LENGTH);
}

function pickAll(hash: string): string[] {
    const result: Set<string> = new Set<string>();
    const str: string = hash + hash;
    for (let i = 0; i < hash.length; i++) {
        result.add(str.substring(i, i + SHORT_URL_LENGTH));
    }

    return [...result];
}

export { SHORT_URL_LENGTH, generateHash, pickFirst, pickAll };