export const chars =
  "0123456789bcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

export default function encodeNum(num: number) {
  let radix = chars.length;

  let last = num;

  let arr: Array<string> = [];

  let mod = 0;

  do {
    mod = last % radix;

    last = (last - mod) / radix;

    arr.push(chars[mod]);
  } while (last);

  let codeStr = arr.join("");

  return codeStr;
}
