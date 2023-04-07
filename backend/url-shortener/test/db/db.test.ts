import { getDb, loadDb } from '../../src/db';

describe('db', () => {
    describe('loadDb', () => {
        it('no config', () => {
            try {
                loadDb();
            } catch (e) {
                expect((e as Error).message).toBe('缺少数据库配置参数！');
            }
        });
        it('with config', () => {
            const db = loadDb({
                client: 'sqlite3',
                connection: {
                    filename: './data.db',
                },
            });
            expect(db).toBeDefined();
        });
    });
    describe('getDb', () => {
        it('have db', () => {
            const db = loadDb({
                client: 'sqlite3',
                connection: {
                    filename: './data.db',
                },
            });
            const db2 = getDb();
            expect(db).toBe(db2);
        });
        it('no db', () => {
            try {
                getDb();
            } catch (e) {
                expect(e).toStrictEqual(new Error('缺少数据库配置参数！'));
            }
        });
        it('no db with config', () => {
            const db = getDb({
                client: 'sqlite3',
                connection: {
                    filename: './data.db',
                },
            });
            expect(db).toBeDefined();
        });
    });
});
