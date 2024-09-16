import MurmurHash3 from "imurmurhash";

function hash(val: string, seed?: number | undefined): number {
    let hashState
    if (seed) {
        hashState = MurmurHash3(val, seed);
    } else {
        hashState = MurmurHash3(val);
    }
    return hashState.result();
}

function hashUrl(url: string, seed?: number | undefined): string {
    let hashResult = hash(url, seed);
    let result = string10to62(hashResult);
    checkHashLength(result)
    return result
}
function checkHashLength(hash: string): boolean {
    if (hash.length > 8) {
        throw new Error('hash too long');
    }
    return true
}
function string10to62(number: number): string {
    let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split(''),
        radix = chars.length,
        qutient = number,
        arr = [];
    do {
        let mod = qutient % radix;
        qutient = (qutient - mod) / radix;
        arr.unshift(chars[mod]);
    } while (qutient);
    return arr.join('');
}

export { hashUrl, hash, checkHashLength }