
const BASE62_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
export function bigintToBase62(num: bigint): string {
  let code = '';
  if (num == 0n) {
    code = '0';
  }
  while (num > 0n) {
    const index = Number(num % 62n);
    code = BASE62_ALPHABET[index] + code;
    num /= 62n;
  }
  return code;
}

export function urlEncode(url: string): string {
  return encodeURIComponent(url);
}

export function urlDecode(url: string): string {
  return decodeURIComponent(url);
}