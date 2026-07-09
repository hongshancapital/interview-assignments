export function convert10to62(num: bigint): string {
    const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    const radix = BigInt(chars.length);
    const arr = [];
    let mod: bigint;
    let base10: bigint = num;
    do {
      mod = base10 % radix;
      base10 = (base10 - mod) / radix;
      arr.unshift(chars[Number(mod)]);
    } while (base10);
    return arr.join('');
  }
  
// export function convert62to10(str: string): number {
//   const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
//   const radix = chars.length;
//   const len = str.length;
//   let i = 0;
//   let base62 = 0;

//   let numCode = String(str);
//   while (i < len) {
//     base62 += Math.pow(radix, i++) * chars.indexOf(numCode.charAt(len - i) || "0");
//   }
//   return base62;
// }