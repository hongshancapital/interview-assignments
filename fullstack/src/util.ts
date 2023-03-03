const BASE62 = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

export function encodeToBase62(val: number): string {
  const len = BASE62.length;
  let outArr = [];
  while (val > 0) {
    outArr.push(BASE62[val % len]);
    val = Math.floor(val / len);
  }
  return outArr.reverse().join('');
}