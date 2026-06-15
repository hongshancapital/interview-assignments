import * as mysql from '../../../src/database/mysql';

describe('Unit tests of <database.mysql>', () => {
    const successHeader = {
        affectedRows: 1,
    };
    const failedHeader = {
        affectedRows: 0,
    };
    describe('test mysqlUpdate', () => {
        let spyWrite: jest.SpyInstance;
        beforeAll(() => {
            spyWrite = jest.spyOn(mysql.writePool, 'query');
        });
        afterAll(() => {
            spyWrite.mockRestore();
        });
        beforeEach(() => {
            spyWrite.mockClear();
        });
        test('when update successful.', () => {
            spyWrite.mockResolvedValue([successHeader, []]);
            expect(mysql.mysqlUpdate('someKey', 'something', 234, 123)).resolves.toBe('someKey');
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('UPDATE short_url SET origin_url = ?, update_time = ? WHERE short_url = ? and update_time = ?',
                ['something', 234, 'someKey', 123]);
        });
        test('when nothing affected.', () => {
            spyWrite.mockResolvedValue([failedHeader, []]);
            expect(mysql.mysqlUpdate('someKey', 'something', 234, 123)).resolves.toBeUndefined();
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('UPDATE short_url SET origin_url = ?, update_time = ? WHERE short_url = ? and update_time = ?',
                ['something', 234, 'someKey', 123]);
        });
        test('when mysql gets error.', () => {
            spyWrite.mockRejectedValue(new Error());
            expect(mysql.mysqlUpdate('someKey', 'something', 234, 123)).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('UPDATE short_url SET origin_url = ?, update_time = ? WHERE short_url = ? and update_time = ?',
                ['something', 234, 'someKey', 123]);
        });
    });

    describe('test mysqlInsert', () => {
        let spyWrite: jest.SpyInstance;
        beforeAll(() => {
            spyWrite = jest.spyOn(mysql.writePool, 'query');
        });
        afterAll(() => {
            spyWrite.mockRestore();
        });
        beforeEach(() => {
            spyWrite.mockClear();
        });
        test('when insert successful.', () => {
            spyWrite.mockResolvedValue([successHeader, []]);
            expect(mysql.mysqlInsert('someKey', 'something', 123)).resolves.toBe('someKey');
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('INSERT INTO short_url (short_url, origin_url, update_time) VALUES (?, ?, ?)', ['someKey', 'something', 123]);
        });
        test('mysqlInsert when mysql nothing affected.', () => {
            spyWrite.mockResolvedValue([failedHeader, []]);
            expect(mysql.mysqlInsert('someKey', 'something', 123)).resolves.toBe(undefined);
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('INSERT INTO short_url (short_url, origin_url, update_time) VALUES (?, ?, ?)', ['someKey', 'something', 123]);
        });
        test('when mysql gets error.', () => {
            spyWrite.mockRejectedValue(new Error());
            expect(mysql.mysqlInsert('someKey', 'something', 123)).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('INSERT INTO short_url (short_url, origin_url, update_time) VALUES (?, ?, ?)', ['someKey', 'something', 123]);
        });
        test('when mysql gets dupilicated key.', () => {
            spyWrite.mockRejectedValue({ code: 'ER_DUP_ENTRY' });
            expect(mysql.mysqlInsert('someKey', 'something', 123)).resolves.toBe(undefined);
            expect(spyWrite).toBeCalledTimes(1);
            expect(spyWrite).lastCalledWith('INSERT INTO short_url (short_url, origin_url, update_time) VALUES (?, ?, ?)', ['someKey', 'something', 123]);
        });
    });

    describe('test mysqlGetMultiInfo', () => {
        let spyRead: jest.SpyInstance;
        beforeAll(() => {
            spyRead = jest.spyOn(mysql.readPool, 'query');
        });
        afterAll(() => {
            spyRead.mockRestore();
        });
        beforeEach(() => {
            spyRead.mockClear();
        });
        test('when mysql return nothing.', () => {
            spyRead.mockResolvedValue([[], []]);
            expect(mysql.mysqlGetMultiInfo(['someKey1', 'someKey2'])).resolves.toStrictEqual([]);
            expect(spyRead).toBeCalledTimes(1);
            expect(spyRead).lastCalledWith('SELECT short_url, origin_url, update_time FROM short_url WHERE short_url IN ( ?, ? )', ['someKey1', 'someKey2']);
        });
        test('mysqlGetMultiInfo when mysql return something.', () => {
            spyRead.mockResolvedValue([[
                { 'origin_url': 'something1' },
                { 'origin_url': 'something2' },
            ], []]);
            expect(mysql.mysqlGetMultiInfo(['someKey1', 'someKey2'])).resolves.toStrictEqual([{ 'origin_url': 'something1' }, { 'origin_url': 'something2' }]);
            expect(spyRead).toBeCalledTimes(1);
            expect(spyRead).lastCalledWith('SELECT short_url, origin_url, update_time FROM short_url WHERE short_url IN ( ?, ? )', ['someKey1', 'someKey2']);
        });

        test('mysqlGetMultiInfo when mysql gets error.', () => {
            spyRead.mockRejectedValue(new Error());
            expect(mysql.mysqlGetMultiInfo(['someKey1', 'someKey2'])).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyRead).toBeCalledTimes(1);
            expect(spyRead).lastCalledWith('SELECT short_url, origin_url, update_time FROM short_url WHERE short_url IN ( ?, ? )', ['someKey1', 'someKey2']);
        });
    });

    describe('test mysqlGet', () => {
        let spyMysqlGetMultiInfo: jest.SpyInstance;
        beforeAll(() => {
            spyMysqlGetMultiInfo = jest.spyOn(mysql, 'mysqlGetMultiInfo');
        });
        afterAll(() => {
            spyMysqlGetMultiInfo.mockRestore();
        });
        beforeEach(() => {
            spyMysqlGetMultiInfo.mockClear();
        });
        test('when mysqlGetMultiInfo returns nothing.', async () => {
            spyMysqlGetMultiInfo.mockResolvedValue([]);
            await expect(mysql.mysqlGet('someKey')).resolves.toBeUndefined();
            expect(spyMysqlGetMultiInfo).toBeCalledTimes(1);
            expect(spyMysqlGetMultiInfo).lastCalledWith(['someKey']);
        });
        test('when mysqlGetMultiInfo returns something.', () => {
            spyMysqlGetMultiInfo.mockResolvedValue([{ 'origin_url': 'something', }]);
            expect(mysql.mysqlGet('someKey')).resolves.toBe('something');
            expect(spyMysqlGetMultiInfo).toBeCalledTimes(1);
            expect(spyMysqlGetMultiInfo).lastCalledWith(['someKey']);
        });
        test('mysqlGet when mysql gets error.', () => {
            spyMysqlGetMultiInfo.mockRejectedValue({ 'status': 500, 'msg': 'Internal server error.' });
            expect(mysql.mysqlGet('someKey')).rejects.toStrictEqual({ 'status': 500, 'msg': 'Internal server error.' });
            expect(spyMysqlGetMultiInfo).toBeCalledTimes(1);
            expect(spyMysqlGetMultiInfo).lastCalledWith(['someKey']);
        });
    });
});