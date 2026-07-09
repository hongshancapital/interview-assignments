const widthstr = require('../src/utils/widthstr')

console.log(widthstr(Number(0).toString(2), 7))
console.log(widthstr(Number(1234567).toString(2), 5))
console.log(widthstr(Number(1234).toString(2), 41))
