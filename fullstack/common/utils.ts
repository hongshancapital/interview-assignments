import { createScale10Number, tranform10to62 } from './cal';

/**
 * 将字符串转为hash,采用随机数字格式转换算法
 * @param str  传入的字符串
 * @result hashStr  
 */
export const stringToHash = (str: string): string => {
    // 采用murmurhash算法生成数字
    const scale10Num = createScale10Number(str);
    // 将数字转成62进制
    const result = tranform10to62(scale10Num);
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