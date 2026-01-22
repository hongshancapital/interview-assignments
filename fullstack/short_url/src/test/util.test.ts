import {numberToShortString, shortStringToNumber, md5Hash} from '../util';

test('util numberToShortString test', () => {
    expect(numberToShortString(0)).toEqual('Q');
    expect(numberToShortString(1)).toEqual('W');
    expect(numberToShortString(62)).toEqual('WQ');
    expect(numberToShortString(10598)).toEqual('Eav');
});

test('util shortStringToNumber test', () => {
    expect(shortStringToNumber('Q')).toEqual(0);
    expect(shortStringToNumber('W')).toEqual(1);
    expect(shortStringToNumber('WQ')).toEqual(62);
    expect(shortStringToNumber('Eav')).toEqual(10598);
});

test('util shortStringToNumber test with illegal input', () => {
    expect(() => shortStringToNumber('Q.')).toThrowError();

});

test('util md5Hash test', () => {
    expect(md5Hash('https://www.baidu.com')).toEqual('f9751de431104b125f48dd79cc55822a');
    expect(md5Hash('https://384354.qq.com/a/b?c=d#eee')).toEqual('ceff17de22f9e246da9e96f4a43d3142');
});
