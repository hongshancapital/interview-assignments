import { md5 } from 'md5js';
import { nanoid } from 'nanoid'

export async function ShortUrlGenerator(url: string, length: number): Promise<Array<string>>{
    // 映射字符数组
    const strArr: string[] = [
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
        "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
        "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
        "Y", "Z"
    ];
    
    let strurlArr: Array<string> = new Array<string>();

    // 引入nanoid，用于生成分布式唯一ＩＤ，与ＵＲＬ合并进行 MD5 计算
    let strNanoId = await nanoid();
    let sMD5EncryptResult: string = md5(url + strNanoId, 32);

    for (let i: number = 0; i<4; i++){
        let sTempSubString: string = sMD5EncryptResult.substring(i*8, (i+1)*8);

        let lHexLong: number = parseInt(sTempSubString, 16);
        let shortUrl: string = "";
        for (let j: number = 0; j<length; j++){
            let index: number = lHexLong & (strArr.length-1);
            shortUrl = shortUrl + strArr[index];
            lHexLong = lHexLong >> 6;
        }

        strurlArr.push(shortUrl);
    }

    return strurlArr;
};
