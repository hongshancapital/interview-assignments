class Base62 {
    private chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    private length = 8;

    constructor(chars?: string, length?: number) {
        this.chars = chars || this.chars;
        this.length = length || this.length;
    }

    public encode(num: number) {
        let result = '';
        while (num > 0) {
            result = this.chars.charAt(num % 62) + result;
            num = Math.floor(num / 62);
        }
        return result.padStart(this.length, this.chars[0]);
    }

    public decode(code: string): number {
        let data = 0;
        for (const c of code) {
            const i = this.chars.indexOf(c);
            data = data * this.chars.length + i;
        }
        return data;
    }
}

export default Base62;
