import { loadDb } from '../../src/db';

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
});
