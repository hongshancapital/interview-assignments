
const target = require('./handler');
const redis = require('./db/redis');

describe('Test handler.ts', () => {
    test('generateSalt', () => {
        expect(target.generateSalt()).not.toEqual(0);
    })

    test('generateShort', () => {
        expect(target.generateShort('test')).toEqual('3pFcZR');
    })
})