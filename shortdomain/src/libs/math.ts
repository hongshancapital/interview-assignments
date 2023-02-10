const CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

/**
 * 10 进制转成 62 进制字符串
 * @param num - 十进制值
 * @returns 
 */
function base10to62(num: number): string {
    const arr = []
    const len = CHARS.length

    while (num > 0) {
        arr.push(CHARS[num % len])
        num = Math.floor(num / len)
    }

    return arr.reverse().join('')
}

function base62to10(num: string): number {
    num = num.split('').reverse().join('')
    let val = 0
    const len = CHARS.length

    for (let i = 0; i < num.length; i++) {
        const c = num[i];
        val += (CHARS.indexOf(c) * Math.pow(len, i));
    }
    return val;
}

export default { base10to62, base62to10 }