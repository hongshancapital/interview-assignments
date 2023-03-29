const md5 = require('blueimp-md5')
const key = 'shorturl'
function generate(url: string) {
    const resUrl = ['','','','']
    const hex = md5(key + url)
    const chars = [ "a", "b", "c", "d", "e", "f", "g", "h",
        "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z"]

    for ( let i = 0; i < 4; i++) {
        let sTempSubString = hex.substring(i * 8, i * 8 + 8)
        let lHexLong = 0x3FFFFFFF & Number('0x' + sTempSubString)
        let outChars = "";
        for (let j = 0; j < 6; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            let index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars += chars[index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        resUrl[i] = outChars;
    }
    const rand = Math.round(Math.random() * (4-0)) + 0
    return resUrl[rand];
}

export {
    generate
}