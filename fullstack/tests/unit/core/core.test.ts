import { generateHash, pickFirst, pickAll } from '../../../src/core/core';

describe('Unit tests of <core>', () => {
    test('generateHash', async () => {
        expect(generateHash('hello')).toBe('XUFAKrxLKna5cZ2REBfFkg');
    });
    test('generateHash', async () => {
        let hash = generateHash('https://world.com');
        let all = pickAll(hash);
        let i = 200;
        let r: string[] = [];
        for (let s of all) {
            r.push(`("${s}", "occupied", ${i++}),`);
        }
        console.log(r);
    });
    test('pickFirst', async () => {
        expect(pickFirst('XUFAKrxLKna5cZ2REBfFkg')).toBe('XUFAKrxL');
    });
    test('pickAll', async () => {
        const all: string[] = [
            'XUFAKrxL', 'UFAKrxLK',
            'FAKrxLKn', 'AKrxLKna',
            'KrxLKna5', 'rxLKna5c',
            'xLKna5cZ', 'LKna5cZ2',
            'Kna5cZ2R', 'na5cZ2RE',
            'a5cZ2REB', '5cZ2REBf',
            'cZ2REBfF', 'Z2REBfFk',
            '2REBfFkg', 'REBfFkgX',
            'EBfFkgXU', 'BfFkgXUF',
            'fFkgXUFA', 'FkgXUFAK',
            'kgXUFAKr', 'gXUFAKrx'
          ];
        expect(pickAll('XUFAKrxLKna5cZ2REBfFkg')).toStrictEqual(all);
    });
});