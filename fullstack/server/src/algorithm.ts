const chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

export function number2Short(num: number, length: number) {
  const shortPath = convertToBase62(num);
  // 生成随机字符串作为短链接的其他部分
  const randomPart = generateRandomString(length - shortPath.length);
  return shortPath + randomPart
}

function convertToBase62(num: number): string {
  let str = "";
  let mod = chars.length
  do {
    const remainder = num % mod;
    str = chars.charAt(remainder) + str;
    num = Math.floor(num / mod);
  } while (num > 0);
  return str;
}

function generateRandomString(length: number): string {
  let str = "";
  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * chars.length);
    str += chars.charAt(randomIndex);
  }
  return str;
}