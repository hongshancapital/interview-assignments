import cryptoRandomString from 'crypto-random-string';

interface INode {
  short: string;
  long: string;
  createTime: Date;
  expireTime: Date;
}
// 短链接字典
var shortMap: Map<string, INode> = new Map<string, INode>();
// 长链接字典
var longMap: Map<string, INode> = new Map<string, INode>();

export function getRandomStr() {
  var randomStr: string = cryptoRandomString({ length: 8, type: 'url-safe' });
  // console.log('randomStr', randomStr);
  return randomStr;
}

// 长链接生成短链
export function toShortUrl(sourceUrl: string) {
  if (longMap.has(sourceUrl)) {
    return longMap.get(sourceUrl).short;
  } else {
    var randomStr: string = getRandomStr();
    // 生成不重复的key，虽然概率很低，但需要防止意外覆盖
    while (shortMap.has(randomStr)) {
      randomStr = getRandomStr();
    }
    var short: string = 'https://abc.cn/' + randomStr;// 短链的域名+随机串
    var newNode: INode = {
      short,
      long: sourceUrl,
      createTime: new Date(),
      expireTime: new Date(Date.now() + 3600 * 24 * 10)
    }
    shortMap.set(short, newNode);
    longMap.set(sourceUrl, newNode);
    return short;
  }
}
// 返回长链接
export function shortToLong(short: string) {
  // console.log('shortToLong', short);
  if (shortMap.has(short)) {
    return shortMap.get(short).long;
  } else {
    return '';
  }
}
// 返回短链接
export function longToShort(long: string) {
  // console.log('longToShort', long);
  if (longMap.has(long)) {
    return longMap.get(long).short;
  } else {
    return toShortUrl(long);
  }
}