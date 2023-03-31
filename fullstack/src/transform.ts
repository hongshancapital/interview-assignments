/**
 * 短链接 id 到 标示 转换
 * 1. 基于乱序字母表(基于[a-zA-Z0-9])转换 以免生成有规律的(类似纯数字)链接
 * 2. 简单字符串加解密
 *  2.1 字符串添加校验码 提高对错误输入的校验效率
 *  2.2 按照校验码重新计算字符串 增加不可预测性
 */

// 字母表
const ALPHABET = "C1ryq6inaTBPmbeKUszc0Agl28NGR4QxwY9uXOvhp5MHZdoLJ7tfkV3FDWSIEj"

// 字符串加解密
class StringEncryptor {
    private static chars = ALPHABET.split("");

    private static swap(str: string, offset: number, reverse = false) {
        const newChars = [...this.chars.slice(offset), ...this.chars.slice(0, offset)]
        return str.split("").map((char) => {
            return reverse ? this.chars[newChars.indexOf(char)] : newChars[this.chars.indexOf(char)];
        }).join("");
    }

    static encrypt(str: string) {
        const checksum = this.checksum(str);
        return `${this.swap(str, this.chars.indexOf(checksum))}${checksum}`;
    }

    static decrypt(str: string) {
        const checksum = str.slice(-1);
        const result = this.swap(str.slice(0, -1), this.chars.indexOf(checksum), true)
        if (this.checksum(result) !== checksum) {
            throw new Error("Invalid checksum");
        }
        return result;
    }

    // 生成校验码(字符串求和)
    private static checksum(str: string) {
        let sum = 0;
        for (let i = 0; i < str.length; i++) {
            sum += str.charCodeAt(i);
        }
        return this.chars[sum % this.chars.length];
    }
}

// 基于字母表的转换
export class BaseConverter {
    private static digits = ALPHABET.split("");
    private static base = this.digits.length;

    public static to(dec: number): string {
        if (dec <= 0) {
            throw new Error("Input must be a non-negative integer");
        }

        let result = "";
        while (dec >= this.base) {
            const remainder = dec % this.base;
            result = this.digits[remainder] + result;
            dec = Math.floor(dec / this.base);
        }
        result = this.digits[dec] + result;
        return result;
    }

    public static from(str: string): number {
        let result = 0;
        const len = str.length;
        for (let i = 0; i < len; i++) {
            const digit = this.digits.indexOf(str[i]);
            if (digit === -1) {
                throw new Error(`Invalid character in input: ${str[i]}`);
            }
            result = result * this.base + digit;
        }
        return result;
    }
}

// 编码
export const encode = (id: number): string => {
    const result = StringEncryptor.encrypt(BaseConverter.to(id));
    if (!/^[a-zA-Z0-9]{1,8}$/.test(result)) {
        throw new Error("Invalid result");
    }
    return result;
}

// 解码
export const decode = (str: string): number => {
    if (!/^[a-zA-Z0-9]{2,8}$/.test(str)) {
        throw new Error("Invalid input");
    }
    return BaseConverter.from(StringEncryptor.decrypt(str));
}
