// 生成特定长度的随机字符串
export function randomString(len: number): string {
  let str = 'abcdefg012ABCDEFGhijklmn345HIJKLMNopqrstOPQRST678uvwxyz0UVWXYZ';
  let res = '';
  let strLen = str.length - 1;
  for (let i = 0; i < len; i++) {
    let n = Math.round(Math.random() * strLen);
    res += str.slice(n, n + 1);
  }
  return res;
}
