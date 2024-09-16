const urlPattern = /https?:\/\/(\w+:?\w*)?(\S+)(:\d+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/;

export function isUrl(str: string) {
  return urlPattern.test(str);
}

/**
 * 是否是短连接
 * 只允许5-7位的数字加字母
 * @param str
 */
export function isShortUrl(str: string) {
    return /^[0-9A-Za-z]{5,7}$/g.test(str);
}
