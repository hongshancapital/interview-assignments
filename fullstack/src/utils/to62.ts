const chars = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
const radix = chars.length;

/**
 * 按照自定义 64 位编码
 */
export function to62(number: number) {
  let result = "";

  do {
    const mod = number % radix;
    number = (number - mod) / radix;
    result = chars[mod] + result;
  } while (number);

  return result;
}
