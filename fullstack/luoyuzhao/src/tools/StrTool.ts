const charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-";
const charCodes = charStr.split('');
const BASE64_RADIX =BigInt(64);
const CHAR_SIZE = 8;
import { Md5 } from 'ts-md5';

class StrTool {
    static getMd5(longUrl:string){
        return Md5.hashStr(longUrl);
    }
    static convert64Base(value: bigint): string {
        let buf = new Array<string>(CHAR_SIZE);
        let charEnd = CHAR_SIZE - 1;
        let negative = (value < 0);
        if (!negative) {
            value= -value;
        }
        while (value <= -BASE64_RADIX) {
            let index=(Number)(-(value % BASE64_RADIX));
            buf[charEnd--] =  charCodes[index];
            value = value / BASE64_RADIX;
        }
        buf[charEnd] =  charCodes[(Number)(-value)];
        if (negative) {
            buf[--charEnd] = '-';
        }
        return buf.join('');
    }
}
export default StrTool;