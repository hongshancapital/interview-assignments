const dictArr: Array<string> = '0abcdfghijklmopqsuvwx1234yz5PerCnt6789ABDEFGHIJKLMNOQRSTUVWXYZ'.split('');
const dictLen: number = dictArr.length;
const MAX_VALUE: number = Math.pow(62, 8) - 1;

interface Base64Dict {
  [key :string]: number
}
let dict: Base64Dict = {};
dictArr.forEach((ele: string, ind: number) : void => {
  dict[ele] = ind;
});

export const encodeBase62 = (decimal: number) : string => {
  let tmp: number = decimal;
  let res: Array<string> = [];

  if (decimal < 0 || decimal > MAX_VALUE) {
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

export const decodeBase62 = (str: string) : number => {
  let res: number = 0;

  /* Math.pow(62, 8) < Number.MAX_SAFE_INTEGER */
  if (!/^[A-Za-z0-9]{0,8}$/.test(str)) {
    return NaN;
  }

  str.split('').reverse().forEach( (char: string, ind: number) : void => {
    res += dict[char] * Math.pow(dictLen, ind);
  });

  return res;
}