const alphabet = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
const radix = alphabet.length;

const radix_bigint = BigInt(radix)

export function base10to62_bigint(number: bigint) {
    let result = "";
    do {
        const mod =  number % radix_bigint;
        number = (number - mod) / radix_bigint;
        result = alphabet[Number(mod)] + result;
    } while (number);
    return result.substring(0, 8);
}

export function base10to62(number: number) {
    let result = "";
    do {
        const mod = number % radix;
        number = (number - mod) / radix;
        result = alphabet[mod] + result;
    } while (number);

    return result.padStart(8, '0');
}

export function validateUrl(url: string) {
    if (url.length > 2048)  return false;

    return new RegExp(
        '^(https?:\\/\\/)' + // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
        '(\\:\\d+)?' + //port
        '(\\/[-a-z\\d%_.~+]*)*' + // path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', // hash
        'i',
    ).test(url);
}