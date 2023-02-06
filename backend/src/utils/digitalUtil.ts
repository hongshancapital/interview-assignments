export function digital10to62(num: number): string {
  const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
  const radix = chars.length;
  const arr = [];
  let mod: number;
  let qutient: number = num;
  do {
    mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars[mod]);
  } while (qutient);
  return arr.join('');
}

export function digital62to10(str: string): number {
  const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
  const radix = chars.length;
  const len = str.length;
  let i = 0;
  let result = 0;

  let numberCode = String(str);
  while (i < len) {
    result += Math.pow(radix, i++) * chars.indexOf(numberCode.charAt(len - i) || "0");
  }
  return result;
}