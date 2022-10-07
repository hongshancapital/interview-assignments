"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
/**
 * 62进制数
 */
exports.default = {
    /**
     * 十进制转62进制
     * @param num 十进制数字
     * @param length 生成的62进制串长度，不够位数的左补0
     * @returns 62进制串
     */
    encode: function (num, length) {
        let result = '';
        let remainder = 0;
        let scale = chars.length;
        do {
            remainder = num % scale;
            result = chars[remainder] + result;
            num = Math.floor(num / scale);
        } while (num > 0);
        if (length) {
            while (result.length < length) {
                result = chars[0] + result;
            }
        }
        return result;
    },
    /**
     * 62进制转十进制
     * @param number62Str 62进制串
     * @returns 十进制数
     */
    decode: function (number62Str) {
        let result = 0;
        for (let i = 0; i < number62Str.length; i++) {
            const char = number62Str[i];
            const num = chars.indexOf(char);
            if (num > 0) {
                result += num * Math.pow(chars.length, number62Str.length - i - 1);
            }
        }
        return result;
    }
};
//# sourceMappingURL=number62.js.map