import crypto from 'crypto';

export const md5Hash = (originUrl: string): string => {
    const hash = crypto.createHash('md5');
    hash.update(originUrl);
    return hash.digest('hex');
};

const strMap = 'QWERTYUIOPASDFGHJKLZXCVBNM0123456789qwertyuiopasdfghjklzxcvbnm';
export const numberToShortString = (n: number) => {
    let r = '';
    let tn = Math.floor(n);
    do {
        r = strMap.charAt(tn%strMap.length) + r;
        tn = Math.floor(tn/strMap.length);
    } while(tn>0)
    return r;
};

const genNumMap = () => {
    const chars = strMap.split('');
    return chars.reduce((total, char, index)=>{
        total.set(char, index);
        return total;
    }, new Map<string, number>());
};

const numMap = genNumMap();

export const shortStringToNumber = (str: string) => {
    let n = 0;
    const chars = str.split('');
    return chars.reduce((n, c) => {
        n*=strMap.length;
        const sn = numMap.get(c);
        if(sn === undefined){
            throw 'Illegal short URL';
        }
        n+=sn;
        return n;
    }, 0);
};
