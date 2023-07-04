import crypto from 'crypto';

/**
 * 
 * @param str  传入的字符串
 * @result hashStr  
 */
export const stringToHash = (str: string): string => {
    if (typeof str !== 'string') {
        return ''
    }
    const hash = crypto.createHash('md5');
    hash.update(str);
    const result = hash.digest('hex').slice(0, 8) // 取hash前8位
    return result;
}

/**
 * 判断字符串是否是域名
 * @param str 传入的字符串
 * @returns true or false
 */
export const checkUrl = (str: string): boolean => {
    if (typeof str !== 'string') {
        return false
    }
    try {
        new URL(str); // 将字符串转换为URL对象
        return true;
    } catch (e) {
        return false
    }
}