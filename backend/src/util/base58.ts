const table = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
const zero = BigInt(0);
const base = BigInt(58);
const minLength = 8;

export const encodeBase58 = (num: bigint) => {
    let res = "";

    while (num > zero) {
        res = table[Number(num % base)] + res;
        num = num / base;
    }

    // 长度不够则补'0'
    if (res.length < minLength) {
        res = '0'.repeat(minLength - res.length) + res;
    }
    return res;
};