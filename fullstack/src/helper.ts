export const BITS = 8;
export const MODE = 62;
export const MAX = 218340105584896; // power(62, 8)

export const INVALID_CHAR = ' ';

export function mapChar(value: number): string {
    if (value>=0 && value<=9) {
        return String.fromCharCode(value + '0'.charCodeAt(0));
    } else if (value>=10 && value<=35) {
        return String.fromCharCode((value - 10) + 'A'.charCodeAt(0));
    } else if (value>=36 && value<=61) {
        return String.fromCharCode((value - 36) + 'a'.charCodeAt(0));
    }
    return INVALID_CHAR;
}

export function map(value: number): string {
    if (value<0 || value>=MAX) {
        return "";
    }
    var url:string = "";
    do {
        var v:number = value % MODE;
        url = mapChar(v) + url;
        value = Math.floor(value / MODE);
    } while (value > 0);
    return url;
}
