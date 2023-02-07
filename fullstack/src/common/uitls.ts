/**
 * 指定长度和进制的UUID
 * @param len       长度
 * @param radix     进制
 * @returns {string}
 */
export const uuid = (len, radix = 62) => {
  var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
  var uuid = [], i;
  radix = radix || chars.length;
  if (len) {
    for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
  } else {
    var r;
    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
    uuid[14] = '4';
    for (i = 0; i < 36; i++) {
      if (!uuid[i]) {
        r = 0 | Math.random() * 16;
        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
      }
    }
  }
  return uuid.join('');
}

export const string10to62 = (num: number) => {
  var chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split(''),
  radix = chars.length,
  qutient = +num,
  arr = [];
  do {
    let mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars[mod]);
  } while (qutient);
  return arr.join('');
}


export const string62to10 = (number_code: string) => {
  var chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ',
    radix = chars.length,
    number_code = String(number_code),
    len = number_code.length,
    i = 0,
    origin_number = 0;
  while (i < len) {
    origin_number += Math.pow(radix, i++) * chars.indexOf(number_code.charAt(len - i) || '0');
  }
  return origin_number;
}
