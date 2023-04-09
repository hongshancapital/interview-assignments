import { Knex } from 'knex';
import { db } from '../../src/db/db';
import { createShortUrlTable } from '../../src/db/shortUrl';

jest.mock('../../src/db/db');

const mockHasTable = jest.fn();
const mockCreateTable = jest.fn();

db.schema = {
    hasTable: mockHasTable,
    createTable: mockCreateTable,
} as unknown as Knex.SchemaBuilder;

describe('db-shortUrl', () => {
    describe('createShortUrlTable', () => {
        beforeEach(() => {
            mockHasTable.mockClear();
            mockCreateTable.mockClear();
        });
        it('exist', async () => {
            mockHasTable.mockResolvedValue(true);
            await createShortUrlTable();
            expect(mockHasTable).toBeCalled();
            expect(mockCreateTable).not.toBeCalled();
        });
        it('not exist', async () => {
            const mockString = jest.fn().mockImplementation(() => {
                return {
                    primary: mockPrimary,
                    unique: mockUnique,
                };
            });
            const mockPrimary = jest.fn();
            const mockUnique = jest.fn();
            mockHasTable.mockResolvedValue(false);
            mockCreateTable.mockImplementation((tableName, cb) => {
                cb({
                    string: mockString,
                });
            });

            await createShortUrlTable();
            expect(mockHasTable).toBeCalled();
            expect(mockCreateTable).toBeCalled();
            expect(mockPrimary).toBeCalled();
            expect(mockString).toBeCalledTimes(2);
            expect(mockString.mock.calls[0]).toStrictEqual(['shortCode', 20]);
            expect(mockString.mock.calls[1]).toStrictEqual(['longUrl', 3000]);
        });
    });
});
