import genShort from "./gen-short";

describe('genShort', () => {
    it('返回字符串的是8位大小写字母和数字组成的', () => {
        expect(/^[A-Za-z0-9]{8}$/.test(genShort())).toEqual(true);
    });

    it('调用1000次返回的值不重复', () => {
        const set = new Set();
        for (let i=0;i<1000;i++) {
            set.add(genShort())
        }
        expect(set.size).toEqual(1000);
    });
});