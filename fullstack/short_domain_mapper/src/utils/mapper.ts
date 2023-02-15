import { AES, SHA256 } from 'crypto-js';

const encryptKey = 'Ch@NGEMe!'; // 注意替换加密密钥

export const URL_REG = new RegExp(
  '[-a-zA-Z0-9@:%._+~#=]{1,256}',
);

/// return true if it's a validate url
export function isValideUrl(url: string) {
  return URL_REG.test(url);
}

export function EncodeUrl(url: string) {
  const d = new Date();
  let salt = SHA256(d.getTime().toString()).toString().substring(0, 8);
  let short_url = AES.encrypt(url + salt, encryptKey)
    .toString()
    .substring(10, 18);
  // 如有需要可以对生成的short_url字符进行筛选，去除URL非法字符
  // 中间10, 18子字符串的分布相对更随机一点
  return {
    url: url,
    short_url: short_url,
    salt: salt,
  };
}
