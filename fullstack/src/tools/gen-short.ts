
const allChar = 'ABCDEFGHIJKLMNOPQRSTUVWXYZzbcdefghijklmnoparstuvwxyz0123456789';

export default function genShort(length: number = 8): string {
    let res: string = '';

    let i=0;
    while (i++ < length) {
        res += allChar[Math.floor(Math.random()*allChar.length)];
    }

    return res;
}
