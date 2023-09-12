import { CommandData } from 'memcached';
import { memcached, memcachedGet, memcachedSet, memcachedRemove } from '../../../src/database/memcached';

describe('Unit tests of <database.memcached>', () => {
    describe('test memcachedGet', () => {
        let spyGet: jest.SpyInstance;
        beforeAll(() => {
            spyGet = jest.spyOn(memcached, 'get');
        });
        afterAll(() => {
            spyGet.mockRestore();
        });
        beforeEach(() => {
            spyGet.mockClear();
        });
        test('when memcached return nothing.', async () => {
            spyGet.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, null, undefined);
            });
            await expect(memcachedGet('someKey')).resolves.toBeUndefined();
            expect(spyGet).toBeCalledTimes(1);
            expect(spyGet).lastCalledWith('someKey', expect.any(Function));
        });
        test('when memcached return something.', async () => {
            spyGet.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, null, 'something');
            });
            await expect(memcachedGet('someKey')).resolves.toBe('something');
            expect(spyGet).toBeCalledTimes(1);
            expect(spyGet).lastCalledWith('someKey', expect.any(Function));
        });
        test('when memcached gets error.', async () => {
            spyGet.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, new Error());
            });
            await expect(memcachedGet('someKey')).rejects.toStrictEqual({'status': 500, 'msg': 'Internal server error.'});
            expect(spyGet).toBeCalledTimes(1);
            expect(spyGet).lastCalledWith('someKey', expect.any(Function));
        });
    });

    describe('test memcachedSet', () => {
        let spySet: jest.SpyInstance;
        beforeAll(() => {
            spySet = jest.spyOn(memcached, 'set');
        });
        afterAll(() => {
            spySet.mockRestore();
        });
        beforeEach(() => {
            spySet.mockClear();
        });
        test('when memcached set success.', async () => {
            spySet.mockImplementation((_key, _value, _lifetime, callback) => {
                callback.call({} as CommandData, null, true);
            });
            await  expect(memcachedSet('someKey', 'someValue')).resolves.toBeTruthy();
            expect(spySet).toBeCalledTimes(1);
            expect(spySet).lastCalledWith('someKey', 'someValue', 1800, expect.any(Function));
        });
        test('when memcached set failed.', async () => {
            spySet.mockImplementation((_key, _value, _lifetime, callback) => {
                callback.call({} as CommandData, null, false);
            });
            await expect(memcachedSet('someKey', 'someValue')).resolves.toBeFalsy();
            expect(spySet).lastCalledWith('someKey', 'someValue', 1800, expect.any(Function));
        });
        test('when memcached gets error.', async () => {
            spySet.mockImplementation((_key, _value, _lifetime, callback) => {
                callback.call({} as CommandData, new Error());
            });
            await expect(memcachedSet('someKey', 'someValue')).resolves.toBeFalsy();
            expect(spySet).toBeCalledTimes(1);
            expect(spySet).lastCalledWith('someKey', 'someValue', 1800, expect.any(Function));
        });
    });

    describe('test memcachedRemove', () => {
        let spyDel: jest.SpyInstance;
        beforeAll(() => {
            spyDel = jest.spyOn(memcached, 'del');
        });
        afterAll(() => {
            spyDel.mockRestore();
        });
        beforeEach(() => {
            spyDel.mockClear();
        });
        test('when memcached del success.', async () => {
            spyDel.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, null, true);
            });
            await expect(memcachedRemove('someKey')).resolves.toBeTruthy();
            expect(spyDel).toBeCalledTimes(1);
            expect(spyDel).lastCalledWith('someKey', expect.any(Function));
        });
        test('when memcached del failed.', async () => {
            spyDel.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, null, false);
            });
            await expect(memcachedRemove('someKey')).resolves.toBeFalsy();
            expect(spyDel).toBeCalledTimes(1);
            expect(spyDel).lastCalledWith('someKey', expect.any(Function));
        });
        test('when memcached del error.', async () => {
            spyDel.mockImplementation((_key, callback) => {
                callback.call({} as CommandData, new Error());
            });
            await expect(memcachedRemove('someKey')).resolves.toBeFalsy();
            expect(spyDel).toBeCalledTimes(1);
            expect(spyDel).lastCalledWith('someKey', expect.any(Function));
        });
    });
});