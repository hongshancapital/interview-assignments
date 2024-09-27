/**
 * 短域名算法
 */

import murmurhash3js from 'murmurhash3js';

// 62进制数组
const scale62Arr: Array<string> = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

/**
 * 根据murmurhash算法将链接转成10进制数字
 * @param str 传入的字符串
 * @returns 10进制数字
 */
export const createScale10Number = (str: string) => {
    let res = murmurhash3js.x86.hash32(str, 10);
    console.log('createScale10Number', str, res);
    return res;
}

/**
 * 将10进制转为62进制
 * @param str10 
 * @returns 
 */
export const tranform10to62 = (str10: string) => {
    const radix: number = scale62Arr.length;
    let qutient: number = +str10;
    const arr: Array<string> = [];

    while (qutient) {
        let mod = qutient % radix;
        qutient = (qutient - mod) / radix;
        arr.unshift(scale62Arr[mod])
    }
    return arr.join('');
}
