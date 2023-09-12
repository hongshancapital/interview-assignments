import { db, cache } from '../../../src/database';
import * as shorten from '../../../src/api/shorten';
import * as unshorten from '../../../src/api/unshorten';
import * as validator from '../../../src/validator/shorten';

describe('Unit tests of <api.shorten>', () => {
    describe('test tryInsertDb', () => {
        let dbInsert: jest.SpyInstance;
        let cacheRemove: jest.SpyInstance;
        beforeAll(() => {
            dbInsert = jest.spyOn(db, 'insert');
            cacheRemove = jest.spyOn(cache, 'remove');
        });
        afterAll(() => {
            dbInsert.mockRestore();
            cacheRemove.mockRestore();
        });
        beforeEach(() => {
            dbInsert.mockClear();
            cacheRemove.mockClear();
        });
        test('when db insert success.', async () => {
            dbInsert.mockResolvedValue('someKey');
            cacheRemove.mockResolvedValue(true);
            await expect(shorten.tryInsertDb('someKey', 'something', 123)).resolves.toBe('someKey');
            expect(dbInsert).toBeCalledTimes(1);
            expect(dbInsert).lastCalledWith('someKey', 'something', 123);
            expect(cacheRemove).toBeCalledTimes(1);
            expect(cacheRemove).lastCalledWith('someKey');
        });
        test('when db insert failed.', async () => {
            dbInsert.mockResolvedValue(undefined);
            cacheRemove.mockResolvedValue(true);
            await expect(shorten.tryInsertDb('someKey', 'something', 123)).resolves.toBe(undefined);
            expect(dbInsert).toBeCalledTimes(1);
            expect(dbInsert).lastCalledWith('someKey', 'something', 123);
            expect(cacheRemove).toBeCalledTimes(0);
        });
        test('when db occurs error.', async () => {
            dbInsert.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.tryInsertDb('someKey', 'something', 123)).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(dbInsert).toBeCalledTimes(1);
            expect(dbInsert).lastCalledWith('someKey', 'something', 123);
            expect(cacheRemove).toBeCalledTimes(0);
        });
    });

    describe('test tryUpdateDb', () => {
        let dbUpdate: jest.SpyInstance;
        let cacheRemove: jest.SpyInstance;
        beforeAll(() => {
            dbUpdate = jest.spyOn(db, 'update');
            cacheRemove = jest.spyOn(cache, 'remove');
        });
        afterAll(() => {
            dbUpdate.mockRestore();
            cacheRemove.mockRestore();
        });
        beforeEach(() => {
            dbUpdate.mockClear();
            cacheRemove.mockClear();
        });
        test('when db update success.', async () => {
            dbUpdate.mockResolvedValue('someKey');
            cacheRemove.mockResolvedValue(true);
            await expect(shorten.tryUpdateDb('someKey', 'something', 234, 123)).resolves.toBe('someKey');
            expect(dbUpdate).toBeCalledTimes(1);
            expect(dbUpdate).lastCalledWith('someKey', 'something', 234, 123);
            expect(cacheRemove).toBeCalledTimes(1);
            expect(cacheRemove).lastCalledWith('someKey');
        });
        test('when db update affects nothing.', async () => {
            dbUpdate.mockResolvedValue(undefined);
            await expect(shorten.tryUpdateDb('someKey', 'something', 234, 123)).resolves.toBe(undefined);
            expect(dbUpdate).toBeCalledTimes(1);
            expect(dbUpdate).lastCalledWith('someKey', 'something', 234, 123);
            expect(cacheRemove).toBeCalledTimes(0);
        });
        test('when db occurs error.', async () => {
            dbUpdate.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.tryUpdateDb('someKey', 'something', 234, 123)).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(dbUpdate).toBeCalledTimes(1);
            expect(dbUpdate).lastCalledWith('someKey', 'something', 234, 123);
            expect(cacheRemove).toBeCalledTimes(0);
        });
    });

    describe('test doPickAnother', () => {
        let spyTryInsertDb: jest.SpyInstance;
        let spyTryUpdateDb: jest.SpyInstance;
        beforeAll(() => {
            spyTryInsertDb = jest.spyOn(shorten, 'tryInsertDb');
            spyTryUpdateDb = jest.spyOn(shorten, 'tryUpdateDb');
        });
        afterAll(() => {
            spyTryInsertDb.mockRestore();
            spyTryUpdateDb.mockRestore();
        });
        beforeEach(() => {
            spyTryInsertDb.mockClear();
            spyTryUpdateDb.mockClear();
        });
        const infos = [
            {
                'short_url': 'someKey1',
                'origin_url': 'old_url1',
                'update_time': 123
            },
            {
                'short_url': 'someKey2',
                'origin_url': 'old_url2',
                'update_time': 121
            },
        ];
        test('when unused candidate is available, tryInsertDb success.', async () => {
            const all = ['someKey1', 'someKey2', 'someKey3'];
            spyTryInsertDb.mockResolvedValue('someKey3');
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe('someKey3');
            expect(spyTryInsertDb).toBeCalledTimes(1);
            expect(spyTryInsertDb).lastCalledWith('someKey3', 'something', 234);
            expect(spyTryUpdateDb).toBeCalledTimes(0);
        });
        test('when unused candidate is not available, spyTryUpdateDb success.', async () => {
            const all = ['someKey1', 'someKey2'];
            spyTryUpdateDb.mockResolvedValue('someKey2');
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe('someKey2');
            expect(spyTryUpdateDb).toBeCalledTimes(1);
            expect(spyTryUpdateDb).lastCalledWith('someKey2', 'something', 234, 121);
            expect(spyTryInsertDb).toBeCalledTimes(0);
        });
        test('when tryInsertDb failed once.', async () => {
            const all = ['someKey1', 'someKey2', 'someKey3', 'someKey4'];
            spyTryInsertDb.mockResolvedValueOnce(undefined);
            spyTryInsertDb.mockResolvedValueOnce('someKey4');
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe('someKey4');
            expect(spyTryInsertDb).toBeCalledTimes(2);
            expect(spyTryInsertDb).nthCalledWith(1, 'someKey3', 'something', 234);
            expect(spyTryInsertDb).nthCalledWith(2, 'someKey4', 'something', 234);
            expect(spyTryUpdateDb).toBeCalledTimes(0);
        });
        test('when tryInsertDb occurs an error.', async () => {
            const all = ['someKey1', 'someKey2', 'someKey3', 'someKey4'];
            spyTryInsertDb.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.doPickAnother('something', 234, all, infos))
                .rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyTryInsertDb).toBeCalledTimes(1);
            expect(spyTryInsertDb).lastCalledWith('someKey3', 'something', 234);
            expect(spyTryUpdateDb).toBeCalledTimes(0);
        });
        test('when tryUpdateDb failed once.', async () => {
            const all = ['someKey1', 'someKey2'];
            spyTryUpdateDb.mockResolvedValueOnce(undefined);
            spyTryUpdateDb.mockResolvedValueOnce('someKey1');
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe('someKey1');
            expect(spyTryUpdateDb).toBeCalledTimes(2);
            expect(spyTryUpdateDb).nthCalledWith(1, 'someKey2', 'something', 234, 121);
            expect(spyTryUpdateDb).nthCalledWith(2, 'someKey1', 'something', 234, 123);
            expect(spyTryInsertDb).toBeCalledTimes(0);
        });
        test('when tryUpdateDb occurs an error.', async () => {
            const all = ['someKey1', 'someKey2'];
            spyTryUpdateDb.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.doPickAnother('something', 234, all, infos))
                .rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyTryUpdateDb).toBeCalledTimes(1);
            expect(spyTryUpdateDb).lastCalledWith('someKey2', 'something', 234, 121);
            expect(spyTryInsertDb).toBeCalledTimes(0);
        });
        test('when tryInsertDb failed, tryUpdateDb success.', async () => {
            const all = ['someKey1', 'someKey2', 'someKey3', 'someKey4'];
            spyTryInsertDb.mockResolvedValue(undefined);
            spyTryUpdateDb.mockResolvedValue('someKey2');
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe('someKey2');
            expect(spyTryInsertDb).toBeCalledTimes(2);
            expect(spyTryInsertDb).nthCalledWith(1, 'someKey3', 'something', 234);
            expect(spyTryInsertDb).nthCalledWith(2, 'someKey4', 'something', 234);
            expect(spyTryUpdateDb).toBeCalledTimes(1);
            expect(spyTryUpdateDb).lastCalledWith('someKey2', 'something', 234, 121);
        });
        test('when tryInsertDb failed, tryUpdateDb also failed.', async () => {
            const all = ['someKey1', 'someKey2', 'someKey3', 'someKey4'];
            spyTryInsertDb.mockResolvedValue(undefined);
            spyTryUpdateDb.mockResolvedValue(undefined);
            await expect(shorten.doPickAnother('something', 234, all, infos)).resolves.toBe(undefined);
            expect(spyTryInsertDb).toBeCalledTimes(2);
            expect(spyTryInsertDb).nthCalledWith(1, 'someKey3', 'something', 234);
            expect(spyTryInsertDb).nthCalledWith(2, 'someKey4', 'something', 234);
            expect(spyTryUpdateDb).toBeCalledTimes(2);
            expect(spyTryUpdateDb).nthCalledWith(1, 'someKey2', 'something', 234, 121);
            expect(spyTryUpdateDb).nthCalledWith(2, 'someKey1', 'something', 234, 123);
        });
    });

    describe('test tryPickAnother', () => {
        let dbGetMultiInfo: jest.SpyInstance;
        let spyDoPickAnother: jest.SpyInstance;
        beforeAll(() => {
            dbGetMultiInfo = jest.spyOn(db, 'getMultiInfo');
            spyDoPickAnother = jest.spyOn(shorten, 'doPickAnother');
        });
        afterAll(() => {
            dbGetMultiInfo.mockRestore();
            spyDoPickAnother.mockRestore();
        });
        beforeEach(() => {
            dbGetMultiInfo.mockClear();
            spyDoPickAnother.mockClear();
        });
        const hash = '0123456789abcdefghijkl';
        const all = [
            '01234567', '12345678', '23456789', '3456789a', '456789ab', '56789abc', '6789abcd', '789abcde',
            '89abcdef', '9abcdefg', 'abcdefgh', 'bcdefghi', 'cdefghij', 'defghijk', 'efghijkl', 'fghijkl0',
            'ghijkl01', 'hijkl012', 'ijkl0123', 'jkl01234', 'kl012345', 'l0123456',
        ];
        const infos = [
            {
                'short_url': '01234567',
                'origin_url': 'url0',
                'update_time': 120
            }, {
                'short_url': '23456789',
                'origin_url': 'url2',
                'update_time': 122
            },
        ];
        test('when getMultiInfo returns marched url.', async () => {
            dbGetMultiInfo.mockResolvedValue(infos);
            await expect(shorten.tryPickAnother(hash, 'url2', 234)).resolves.toBe('23456789');
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(0);
        });

        test('when dbGetMultiInfo returns info, doPickAnother success.', async () => {
            dbGetMultiInfo.mockResolvedValue(infos);
            spyDoPickAnother.mockResolvedValue('12345678');
            await expect(shorten.tryPickAnother(hash, 'url1', 234)).resolves.toBe('12345678');
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(1);
            expect(spyDoPickAnother).lastCalledWith('url1', 234, all, infos);
        });

        test('when dbGetMultiInfo returns info, doPickAnother failed.', async () => {
            dbGetMultiInfo.mockResolvedValue(infos);
            spyDoPickAnother.mockResolvedValue(undefined);
            await expect(shorten.tryPickAnother(hash, 'url1', 234)).rejects.toStrictEqual({"msg": "Failed to shorten url: url1.", "status": 500});
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(1);
            expect(spyDoPickAnother).lastCalledWith('url1', 234, all, infos);
        });

        test('when dbGetMultiInfo returns nothing, doPickAnother success.', async () => {
            dbGetMultiInfo.mockResolvedValue([]);
            spyDoPickAnother.mockResolvedValue('12345678');
            await expect(shorten.tryPickAnother(hash, 'url1', 234)).resolves.toBe('12345678');
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(1);
            expect(spyDoPickAnother).lastCalledWith('url1', 234, all, []);
        });

        test('when dbGetMultiInfo returns nothing, doPickAnother failed.', async () => {
            dbGetMultiInfo.mockResolvedValue([]);
            spyDoPickAnother.mockResolvedValue(undefined);
            await expect(shorten.tryPickAnother(hash, 'url1', 234)).rejects.toStrictEqual({"msg": "Failed to shorten url: url1.", "status": 500});
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(1);
            expect(spyDoPickAnother).lastCalledWith('url1', 234, all, []);
        });

        test('tryPickAnother when dbGetMultiInfo occurs an error.', async () => {
            dbGetMultiInfo.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.tryPickAnother(hash, 'url1', 234))
                .rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(0);
        });

        test('tryPickAnother dbGetMultiInfo returns info, when doPickAnother occurs an error.', async () => {
            dbGetMultiInfo.mockResolvedValue(infos);
            spyDoPickAnother.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            await expect(shorten.tryPickAnother(hash, 'url1', 234))
                .rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(dbGetMultiInfo).toBeCalledTimes(1);
            expect(dbGetMultiInfo).lastCalledWith(all);
            expect(spyDoPickAnother).toBeCalledTimes(1);
            expect(spyDoPickAnother).lastCalledWith('url1', 234, all, infos);
        });
    });

    describe('test shortenUrl', () => {
        let spyTryFetchOrigin: jest.SpyInstance;
        let spyTryInsertDb: jest.SpyInstance;
        let spyTryPickAnother: jest.SpyInstance;
        beforeAll(() => {
            spyTryFetchOrigin = jest.spyOn(unshorten, 'tryFetchOrigin');
            spyTryInsertDb = jest.spyOn(shorten, 'tryInsertDb');
            spyTryPickAnother = jest.spyOn(shorten, 'tryPickAnother');
            jest.useFakeTimers().setSystemTime(new Date('2023-01-01')); // timestamp: 1672531200000
        });
        afterAll(() => {
            spyTryFetchOrigin.mockRestore();
            spyTryInsertDb.mockRestore();
            spyTryPickAnother.mockRestore();
            jest.useRealTimers();
        });
        beforeEach(() => {
            spyTryFetchOrigin.mockClear();
            spyTryInsertDb.mockClear();
            spyTryPickAnother.mockClear();
        });
        test('when tryFetchOrigin occurs an error.', async () => {
            spyTryFetchOrigin.mockRejectedValue('wrong');
            await expect(shorten.shortenUrl('something')).rejects.toBe('wrong');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(0);
        });
        test('when url exists in cache or db.', async () => {
            spyTryFetchOrigin.mockResolvedValue('something');
            await expect(shorten.shortenUrl('something')).resolves.toBe('Q3uTDbhL');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(0);
        });
        test('when url not exists in cache or db.', async () => {
            spyTryFetchOrigin.mockResolvedValue('something1');
            spyTryPickAnother.mockResolvedValue('someKey');
            await expect(shorten.shortenUrl('something')).resolves.toBe('someKey');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(0);
            expect(spyTryPickAnother).toBeCalledTimes(1);
            expect(spyTryPickAnother).lastCalledWith('Q3uTDbhLgHnC3YBKcZNrXw', 'something', 1672531200000);
        });
        test('when tryFetchOrigin return undefined, insertDb success.', async () => {
            spyTryFetchOrigin.mockResolvedValue(undefined);
            spyTryInsertDb.mockResolvedValue('Q3uTDbhL');
            await expect(shorten.shortenUrl('something')).resolves.toBe('Q3uTDbhL');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(1);
            expect(spyTryInsertDb).lastCalledWith('Q3uTDbhL', 'something', 1672531200000);
        });
        test('when tryFetchOrigin return empty string, insertDb success.', async () => {
            spyTryFetchOrigin.mockResolvedValue('');
            spyTryInsertDb.mockResolvedValue('Q3uTDbhL');
            await expect(shorten.shortenUrl('something')).resolves.toBe('Q3uTDbhL');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(1);
            expect(spyTryInsertDb).lastCalledWith('Q3uTDbhL', 'something', 1672531200000);
        });
        test('when tryFetchOrigin return undefined, insertDb failed, tryPickAnother success.', async () => {
            spyTryFetchOrigin.mockResolvedValue(undefined);
            spyTryInsertDb.mockResolvedValue(undefined);
            spyTryPickAnother.mockResolvedValue('Q3uTDbhL');
            await expect(shorten.shortenUrl('something')).resolves.toBe('Q3uTDbhL');
            expect(spyTryFetchOrigin).toBeCalledTimes(1);
            expect(spyTryFetchOrigin).lastCalledWith('Q3uTDbhL');
            expect(spyTryInsertDb).toBeCalledTimes(1);
            expect(spyTryInsertDb).lastCalledWith('Q3uTDbhL', 'something', 1672531200000);
            expect(spyTryPickAnother).toBeCalledTimes(1);
            expect(spyTryPickAnother).lastCalledWith('Q3uTDbhLgHnC3YBKcZNrXw', 'something', 1672531200000);
        });
    });

    describe('test shorten', () => {
        let spyValidate: jest.SpyInstance;
        let spyShortenUrl: jest.SpyInstance;
        beforeAll(() => {
            spyValidate = jest.spyOn(validator, 'validate');
            spyShortenUrl = jest.spyOn(shorten, 'shortenUrl');
        });
        afterAll(() => {
            spyValidate.mockRestore();
            spyShortenUrl.mockRestore();
        });
        beforeEach(() => {
            spyValidate.mockClear();
            spyShortenUrl.mockClear();
        });
        test('when validate failed.', async () => {
            spyValidate.mockRejectedValue('wrong');
            await expect(shorten.default('something')).rejects.toBe('wrong');
            expect(spyValidate).toBeCalledTimes(1);
            expect(spyValidate).lastCalledWith('something');
            expect(spyShortenUrl).toBeCalledTimes(0);
        });
        test('when validate success, shortenUrl failed.', async () => {
            spyValidate.mockResolvedValue('something');
            spyShortenUrl.mockRejectedValue('wrong');
            await expect(shorten.default('something')).rejects.toBe('wrong');
            expect(spyValidate).toBeCalledTimes(1);
            expect(spyValidate).lastCalledWith('something');
            expect(spyShortenUrl).toBeCalledTimes(1);
            expect(spyShortenUrl).lastCalledWith('something');
        });
        test('when validate success, shortenUrl success.', async () => {
            spyValidate.mockResolvedValue('something');
            spyShortenUrl.mockResolvedValue('someKey');
            await expect(shorten.default('something')).resolves.toBe('someKey');
            expect(spyValidate).toBeCalledTimes(1);
            expect(spyValidate).lastCalledWith('something');
            expect(spyShortenUrl).toBeCalledTimes(1);
            expect(spyShortenUrl).lastCalledWith('something');
        });
    });
});