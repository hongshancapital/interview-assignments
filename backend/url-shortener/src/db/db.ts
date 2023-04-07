import { Knex, knex } from 'knex';

/**
 * 缓存 db
 */
let db: Knex;

/**
 * 加载数据库
 * @param config 数据源配置
 * @returns 数据库操作函数
 */
function loadDb(config?: Knex.Config): Knex {
    if (!config) {
        throw new Error('缺少数据库配置参数！');
    }
    db = knex(config);
    return db;
}

/**
 * 获取数据库操作函数
 * @param config 数据源配置
 * @returns 数据库操作函数
 */
function getDb(config?: Knex.Config): Knex {
    return db ?? loadDb(config);
}

/**
 * 关闭数据库
 */
async function closeDb(): Promise<void> {
    await getDb().destroy();
}

export { loadDb, getDb, closeDb };
