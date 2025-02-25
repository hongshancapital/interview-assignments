const BASE_TIMESTAMP = 1679821920; // 2023-03-26 17:12:00 的时间戳除以1000

// '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ' 的随机打乱，避免被破解
const HEX_KEY = 'xt5hTld9AHgXIaLJCwscBPnvpoji8ONZSEeRK7Q3FVuM0kWq6U1mrfbzDGY4y2';
const SHORT_URL_DOMAIN = 'su.co';

/**
 * 将十进制数字转换为62进制
 * @param num 十进制数字
 * @returns 62进制字符串
 */
const hex62 = (num: number): string => {
  let result = '';
  while (num > 0) {
    result = HEX_KEY[num % 62] + result;
    num = Math.floor(num / 62);
  }
  return result;
};

/**
 * 生成两位随机字符串
 * @returns
 */
const randomTwoChar = (): string => {
  return HEX_KEY[Math.floor(Math.random() * 62)] + HEX_KEY[Math.floor(Math.random() * 62)];
};

/**
 * 生成短链接
 * @returns 短链接
 */
export const generateShortUrl = (): string => {
  // 从当前到基准时间经过的秒数，不会重复
  const timestamp = Math.floor(Date.now() / 1000) - BASE_TIMESTAMP;
  // 根据时间不同，时间戳长度在1-6之间，可以覆盖1800年的时间跨度
  const timeOfHex62 = hex62(timestamp);
  // 增加两位随机字符串，避免被破解，且有效避免并发导致的重复
  return `${SHORT_URL_DOMAIN}/${randomTwoChar()}${timeOfHex62}`;
};
