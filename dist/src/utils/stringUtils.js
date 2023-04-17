"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function getStr62ByNumber(number) {
    const chars = 'abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789'.split('');
    const radix = chars.length;
    let qutient = +number;
    let arr = [];
    do {
        const mod = qutient % radix;
        qutient = (qutient - mod) / radix;
        arr.unshift(chars[mod]);
    } while (qutient);
    return arr.join('');
}
exports.default = { getStr62ByNumber };
//# sourceMappingURL=stringUtils.js.map