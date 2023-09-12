import * as unshorten from '../../../src/api/unshorten';
import { db, cache } from '../../../src/database';
describe('Unit tests of <api.unshorten>', () => {
    describe('test tryGetFromDb', () => {
        let cacheSet: jest.SpyInstance;
        let dbGet: jest.SpyInstance;
        beforeAll(() => {
            cacheSet = jest.spyOn(cache, 'set');
            dbGet = jest.spyOn(db, 'get');
        });
        afterAll(() => {
            cacheSet.mockRestore();
            dbGet.mockRestore();
        });
        beforeEach(() => {
            cacheSet.mockClear();
            dbGet.mockClear();
        });
        test('when get data from db success.', async () => {
            dbGet.mockResolvedValue('something');
            cacheSet.mockReturnValue(true);
            await expect(unshorten.tryGetFromDb('someKey')).resolves.toBe('something');
            expect(dbGet).toBeCalledTimes(1);
            expect(dbGet).lastCalledWith('someKey');
            expect(cacheSet).toBeCalledTimes(1);
            expect(cacheSet).lastCalledWith('someKey', 'something');
        });
        test('when get data from db failed.', async () => {
            dbGet.mockResolvedValue(undefined);
            cacheSet.mockReturnValue(true);
            await expect(unshorten.tryGetFromDb('someKey')).resolves.toBe(undefined);
            expect(dbGet).toBeCalledTimes(1);
            expect(dbGet).lastCalledWith('someKey');
            expect(cacheSet).toBeCalledTimes(1);
            expect(cacheSet).lastCalledWith('someKey', '');
        });
        test('tryGetFromDb when get data from db occurs an error.', async () => {
            dbGet.mockRejectedValue({'status': 500, 'msg': 'Internal server error.'});
            cacheSet.mockReturnValue(true);
            await expect(unshorten.tryGetFromDb('someKey')).rejects.toStrictEqual({'status': 500, 'msg': 'Internal server error.'});
            expect(dbGet).toBeCalledTimes(1);
            expect(dbGet).lastCalledWith('someKey');
            expect(cacheSet).toBeCalledTimes(0);
        });
    });

    describe('test tryFetchOrigin', () => {
        let cacheGet: jest.SpyInstance;
        let spyTryGetFromDb: jest.SpyInstance;
        beforeAll(() => {
            cacheGet = jest.spyOn(cache, 'get');
            spyTryGetFromDb = jest.spyOn(unshorten, 'tryGetFromDb');
        });
        afterAll(() => {
            cacheGet.mockRestore();
            spyTryGetFromDb.mockRestore();
        });
        beforeEach(() => {
            cacheGet.mockClear();
            spyTryGetFromDb.mockClear();
        });
        test('when get data from cache.', async () => {
            cacheGet.mockResolvedValue('something');
            await expect(unshorten.tryFetchOrigin('someKey')).resolves.toBe('something');
            expect(cacheGet).toBeCalledTimes(1);
            expect(cacheGet).lastCalledWith('someKey');
            expect(spyTryGetFromDb).toBeCalledTimes(0);
        });
        test('when occurs an error.', async () => {
            cacheGet.mockRejectedValue({'status': 500, 'msg': 'Internal server error.'});
            await expect(unshorten.tryFetchOrigin('someKey')).rejects.toStrictEqual({'status': 500, 'msg': 'Internal server error.'});
            expect(cacheGet).toBeCalledTimes(1);
            expect(cacheGet).lastCalledWith('someKey');
            expect(spyTryGetFromDb).toBeCalledTimes(0);
        });
        test('when spyTryGetFromDb success.', async () => {
            cacheGet.mockResolvedValue(undefined);
            spyTryGetFromDb.mockResolvedValue('something1');
    
            await expect(unshorten.tryFetchOrigin('someKey')).resolves.toBe('something1');
            expect(cacheGet).toBeCalledTimes(1);
            expect(cacheGet).lastCalledWith('someKey');
            expect(spyTryGetFromDb).toBeCalledTimes(1);
            expect(spyTryGetFromDb).lastCalledWith('someKey');
        });
        test('when spyTryGetFromDb returns nothing.', async () => {
            cacheGet.mockResolvedValue(undefined);
            spyTryGetFromDb.mockResolvedValue(undefined);
            await expect(unshorten.tryFetchOrigin('someKey')).resolves.toBe(undefined);
            expect(cacheGet).toBeCalledTimes(1);
            expect(cacheGet).lastCalledWith('someKey');
            expect(spyTryGetFromDb).toBeCalledTimes(1);
            expect(spyTryGetFromDb).lastCalledWith('someKey');
        });
    });

    describe('test unshorten', () => {
        let spyTryFetchOrigin: jest.SpyInstance;
        beforeAll(() => {
            spyTryFetchOrigin = jest.spyOn(unshorten, 'tryFetchOrigin');
        });
        afterAll(() => {
            spyTryFetchOrigin.mockRestore();
        });
        beforeEach(() => {
            spyTryFetchOrigin.mockClear();
        });
        test('when validate failed.', async () => {
            await expect(unshorten.default('someKey')).rejects.toStrictEqual({'status': 400, 'msg': 'Please input a valid short url.'});
            expect(spyTryFetchOrigin).toBeCalledTimes(0);
        });
        test('when validate success, spyTryFetchOrigin returns undefined.', async () => {
            spyTryFetchOrigin.mockResolvedValue(undefined);
            await expect(unshorten.default('someKey1')).rejects.toStrictEqual({'status': 404, 'msg': `Short url: someKey1 is not found.`});
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('someKey1');
        });
        test('when validate success, spyTryFetchOrigin returns nothing.', async () => {
            spyTryFetchOrigin.mockResolvedValue('');
            await expect(unshorten.default('someKey1')).rejects.toStrictEqual({'status': 404, 'msg': `Short url: someKey1 is not found.`});
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('someKey1');
        });
        test('when spyTryFetchOrigin returns something.', async () => {
            spyTryFetchOrigin.mockResolvedValue('something');
            await expect(unshorten.default('someKey1')).resolves.toBe('something');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('someKey1');
        });
        test('when spyTryFetchOrigin occurs an error.', async () => {
            spyTryFetchOrigin.mockRejectedValue({'status': 500, 'msg': 'Internal server error.'});
            await expect(unshorten.default('someKey1')).rejects.toStrictEqual({'status': 500, 'msg': 'Internal server error.'});
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('someKey1');
        });
    });
});