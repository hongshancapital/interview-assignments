let base62 = require('base62/lib/custom');

let charset = "~9876543210ABCDEFGHIJKLMNOPQRSTU$#@%!abcdefghijklmnopqrstuvw-=";

charset = base62.indexCharset(charset);

console.log(base62.encode(999, charset));  // "F3"
console.log(base62.decode("F3", charset)); // 999

// 十万亿
console.log(base62.encode(99999999999999, charset));  // "RN@S%~0Q"

