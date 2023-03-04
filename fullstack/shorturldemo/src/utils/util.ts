/**
 * @method isEmpty
 * @param {String | Number | Object} value
 * @returns {Boolean} true & false
 * @description this value is Empty Check
 */
export const isEmpty = (value: string | number | object): boolean => {
  if (value === null) {
    return true;
  } else if (typeof value !== 'number' && value === '') {
    return true;
  } else if (typeof value === 'undefined' || value === undefined) {
    return true;
  } else if (value !== null && typeof value === 'object' && !Object.keys(value).length) {
    return true;
  } else {
    return false;
  }
};

/**
 * 10进制转62进制字符串 
 * @param number 10进制数字
 * @returns 62进制字符串
 */
export function string10to62(number: number) {
  const chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
  const charsArr = chars.split('');
  const radix = chars.length;
  let qutient = +number;
  let arr = [];
  do{
      let mod = qutient % radix;
      qutient = (qutient - mod) / radix;
      arr.unshift(charsArr[mod]);
  }while(qutient);
  return arr.join('');
}
