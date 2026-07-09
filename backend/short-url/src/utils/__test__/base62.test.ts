import Base62 from '@utils/base62';

describe('base62 test', () => {
    const base62 = new Base62();

    it('encode number to base62', async () => {
        const input = 62 ** 7 + Math.floor(Math.random() * 62 ** 7);
        const code = base62.encode(input);
        const id = base62.decode(code);
        expect(code.length).toEqual(8);
        expect(id).toEqual(input);
    });
});
