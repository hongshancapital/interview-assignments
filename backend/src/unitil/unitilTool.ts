const crypto = require('crypto');
export { };
const stringRandom = require('string-random');

const chars: string[] = [
    "0", "1", "2", "3", "4", "5",
    "6", "7", "8", "9", "a", "b",
    "c", "d", "e", "f", "g", "h",
    "i", "j", "k", "l", "m", "n",
    "o", "p", "q", "r", "s", "t",
    "u", "v", "w", "x", "y", "z",
    "A", "B", "C", "D", "E", "F",
    "G", "H", "I", "J", "K", "L",
    "M", "N", "O", "P", "Q", "R",
    "S", "T", "U", "V", "W", "X",
    "Y", "Z"];

function getChar(index: number): string {
    return chars[index];
}


function tinyDomainName(longName: string): string[] {
    const randomStr = stringRandom();
    const realUrl = randomStr + longName;
    const md5Url = crypto.createHash('md5').update(realUrl).digest('hex');
    const resTinyNames: string[] = [];
    for (let begin = 0; begin < 4; begin++) {
        let out = '';
        const end = begin + 1;
        const strPort = md5Url.substring(begin * 8, end * 8);
        let code = parseInt(strPort, 16) & 0x3FFFFFFF;
        for (let i = 0; i < 6; i++) {
            const idx = 0x0000003D & code;
            out += getChar(idx);
            code = code >> 5;
        }
        resTinyNames.push(out);
    }
    return resTinyNames;
};
function formatUrl(url: string): string {
    return `https://127.0.0.1:3000/api/v1/tinyName/${url}`;
}
module.exports = {
    tinyDomainName,
    formatUrl
}
