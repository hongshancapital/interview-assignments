const BASE_62_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
const BASE = BASE_62_CHAR.length;
// 62进制转10进制
const toBase10 = (str: string) => {
  const baseArr = Array.from(str).reverse();
  let result: number = 0;
  baseArr.forEach((item, index) => {
    const base62Index: number = BASE_62_CHAR.indexOf(item);
    result += base62Index * Math.pow(BASE, index);
  });
  return result;
}

// 10进制转62进制
const toBase62 = (num: number) => {
  const result: string[] = [];
  let baseNum = num;
  while (baseNum > 0) {
    result.push(BASE_62_CHAR[baseNum % BASE]);
    baseNum = Math.floor( baseNum / BASE );
  }
  const base62Atr = result.reverse();
  return base62Atr.length > 0 ? base62Atr.join('') : '0';
};

export default {
  toBase10,
  toBase62,
};
