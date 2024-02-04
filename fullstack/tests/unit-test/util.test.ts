import { getHash, convertBase10ToBase62, StringEncoder } from '../../src/util'
import * as constant from '../../src/constant'
test('test get hash', () => {
    expect(getHash('1')).toEqual(getHash('1'))
    
})

test('test convert decimal to 62 hexadecimal', () => {
    expect(convertBase10ToBase62(61)).toEqual('z') 
    expect(convertBase10ToBase62(62)).toEqual('10')
    expect(convertBase10ToBase62(218340105584895)).toEqual('zzzzzzzz')
})

test('string encoder', () => {
    expect(StringEncoder.prototype.generateId).toThrowError();
    const encoder = new StringEncoder('www.test.org')
    expect(encoder.hash).toHaveLength(64);
    expect(encoder.generateId()).toHaveLength(8)
    expect(encoder.generateId()).toMatch(/^[0-9a-zA-Z]{8}$/);
    // @ts-expect-error
    encoder.index = 63
    expect(encoder.generateId()).toHaveLength(8)

    const mock = jest.replaceProperty(constant, 'MAX_LENGTH_SHORT_ID', 1);
    expect(encoder.generateId()).toHaveLength(1);
    mock.restore()
})