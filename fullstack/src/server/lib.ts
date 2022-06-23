export class Shorter {
    private static readonly alphabet: string[] = [
        '8', 'M', 'j', 'e', '0', 'O', 'l', '4', 'u', 'N',
        's', 'x', 'g', 'B', 'd', 'h', 'a', 'p', 'C', 'G',
        'b', 'Y', '7', 'f', 'F', 'W', 'w', 'k', 'c', 'X',
        'V', 'v', 'i', 'n', 'z', 'R', '2', 'E', 'T', 'y',
        'I', 'o', 'U', 'K', 't', 'm', 'q', 'L', 'H', 'S',
        '6', '1', 'Z', '3', 'Q', '5', 'J', 'D', 'r', 'A',
        '9', 'P',
    ];

    private static readonly baseNum: number = 100000000;

    public static idToStr(id: number): string {
        let num = id + Shorter.baseNum;
        let arr: number[] = [];
        while (num > 0) {
            arr.push(Math.trunc(num % 62));
            num = Math.trunc(num / 62);
        }
        let str = '';
        for (let i = arr.length - 1; i >= 0; i--) {
            str = str + Shorter.alphabet[arr[i]]
        }
        return str
    }

    public static strToId(str: string): number {
        let arr: number[] = str.split('').reverse().map((s: string) => Shorter.findIndex(s))
        let num = 0;
        for (let i = arr.length - 1; i >= 0; i--) {
            num += arr[i] * Math.pow(62, i)
        }
        return num - Shorter.baseNum
    }

    private static findIndex(s: string): number {
        for (let i = 0; i < Shorter.alphabet.length; i++) {
            if (s === Shorter.alphabet[i]) {
                return i;
            }
        }
        return -1;
    }
}