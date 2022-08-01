/** @format */

/**
 * 分割url的域名和其他信息，用数组返回
 * @param url
 */
export const splitUrl = (url: string) => {
  let index = 0;
  let count = 0;
  for (let i = 0; i < url.length; i++) {
    if (url.charAt(i) == '/') {
      count++;
      if (count == 3) {
        index = i;
        break;
      }
    }
  }
  return [url.slice(0, index + 1), url.slice(index + 1, url.length)];
};
