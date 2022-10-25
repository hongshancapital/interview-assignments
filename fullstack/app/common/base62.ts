const dictArr: Array<string> = '0abcdfghijklmopqsuvwx1234yz5PerCnt6789ABDEFGHIJKLMNOQRSTUVWXYZ'.split('');
const dictLen: number = dictArr.length;
const MAX_VALUE: number = Math.pow(62, 8) - 1;

export const encodeBase62 = (decimal: number) : string => {
  let tmp: number = decimal;
  let res: Array<string> = [];

  if (isNaN(decimal) || decimal < 0 || decimal > MAX_VALUE) {
    return '';
  }

  while(true) {
    let remainder: number = tmp % dictLen;
    res.unshift(dictArr[remainder]);

    tmp = Math.floor(tmp / dictLen);
    if (tmp === 0) {
      break;
    }
  }

  return res.join('');
}