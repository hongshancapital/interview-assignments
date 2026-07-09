import config from './config/config';

class AError extends Error {
    errorCode: string;
    innErrorCode: string
}

/**
 * 抛出异常
 * @param {string} code      请求返回码
 * @param {string} message   详细描述
 * @param {string} [internalCode] 系统内部唯一编码，方便搜索日志。
 */
export function throwErr(code: string, message: string, internalCode: string): AError {
    let err = new AError(message);
    err.errorCode = code;
    if (internalCode) {
        err.innErrorCode = internalCode;
    }
    throw err;
}

// 使用乱序的码表
const BASE_STR_62 = 'fH1PQ0UN2bIChyGFszLao9ixMYwK3vBeXjkT8pORdqZnW74D5mSEuJtrlA6gVc';
/**
 * 10 进制转为 62进制
 * @param val 
 * @returns 
 */
export function encodeToBase62(val: number): string {
    const len = BASE_STR_62.length;
    let outArr = [];
    while (val > 0) {
        outArr.push(BASE_STR_62[val % len]);
        val = Math.floor(val / len);
    }
    return outArr.reverse().join('');
}

/**
 * 62 进制转为 10 进制
 * @param val 
 * @returns 
 */
export function decodeTo10(val: string): number {
    let outInt = 0;
    for (let i = val.length - 1, j = 0; i >= 0; i--, j++) {
        outInt += BASE_STR_62.indexOf(val[i]) * Math.pow(BASE_STR_62.length, j);
    }
    return outInt;
}

/**
 * 检验 URL 格式
 * @param val 
 * @returns 
 */
export function checkURL(val: string | URL): URL {
    try {
        return new URL(val);
    } catch (e) {
        this.throwErr('406_wrong_url', `参数格式错误[val=${val}]`, 'ER20230217161148');
    }
}

/**
 * 检查短链接格式
 * @param val 
 */
export function checkShortUrl(val: string | URL): void {
    let url: URL = this.checkURL(val);
    if (url && url.origin !== config.shortHost) {
        this.throwErr('406_wrong_host', `参数格式错误[val=${val}]`, 'ER20230217161228');
    }
}

/**
 * 判断短码是否有效
 * @param val 
 * @returns 
 */
export function isShortPathValid(val: string): boolean {
    return RegExp(`^[${BASE_STR_62}]{1,8}$`).test(val);
}
