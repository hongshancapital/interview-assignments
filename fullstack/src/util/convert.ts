// leetcode-cn.com/circle/discuss/EkCOT9/
const CharMap =
  'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

const ALowCharCode = 'a'.charCodeAt(0);
const AHighCharCode = 'A'.charCodeAt(0);

const ZLowCharCode = 'z'.charCodeAt(0);
const ZHighCharCode = 'Z'.charCodeAt(0);

const ZeroCharCode = '0'.charCodeAt(0);
const NineCharCode = '9'.charCodeAt(0);

export function IdToShortURL(n: number): string {
  let shorturl = '';
  while (n) {
    shorturl = CharMap[n % 62] + shorturl;
    n = Math.floor(n / 62);
  }
  return shorturl;
}

export function ShortUrlToId(str: string): number {
  let id = 0;
  for (let i = 0; i < str.length; i++) {
    const charCode = str.charCodeAt(i);
    if (ALowCharCode <= charCode && charCode <= ZLowCharCode) {
      id = id * 62 + (charCode - ALowCharCode);
    } else if (AHighCharCode <= charCode && charCode <= ZHighCharCode) {
      id = id * 62 + (charCode - AHighCharCode + 26);
    } else if (ZeroCharCode <= charCode && charCode <= NineCharCode) {
      id = id * 62 + (charCode - ZeroCharCode + 52);
    }
  }
  return id;
}
