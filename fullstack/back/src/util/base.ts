
// 10进制转62进制
// 0-9a-zA-Z 适合短链接显示
export function string10to62(num: bigint) {
    let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split(''),
        radix: bigint = BigInt(chars.length),
        qutient: bigint = num,
        arr: String[] = [];
    do {
        let mod: bigint = qutient % radix;
        qutient = (qutient - mod) / radix;
        arr.unshift(chars[Number(mod)]);
    } while (qutient);
    return arr.join('');
}