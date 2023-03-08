const CHARSET = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

const indexCharset = function indexCharset(str) {
    let byCode: Record<number, string> = {},
        byChar: Record<string, number> = {},
        char;
    for (let i = 0; i < str.length; i++) {
        char = str[i];
        byCode[i] = char;
        byChar[char] = i;
    }
    return { byCode: byCode, byChar: byChar, length: str.length };
};

const { byCode, byChar, length } = indexCharset(CHARSET);

export const encode = (int: number) => {
    if (int === 0) {
        return byCode[0];
    }

    let res = '';
    while (int > 0) {
        res = byCode[int % length] + res;
        int = Math.floor(int / length);
    }
    return res;
};

export const decode = (str: string) => {
    let res = 0,
        char;
    for (let i = 0; i < str.length; i++) {
        char = str[i];
        res += byChar[char] * Math.pow(length, str.length - i - 1);
    }
    return res;
};
