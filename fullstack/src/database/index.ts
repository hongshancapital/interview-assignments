import { memcachedGet, memcachedSet, memcachedRemove } from './memcached';
import { mysqlGet, mysqlInsert, mysqlUpdate, mysqlGetMultiInfo } from './mysql';

const cache = {
    get: memcachedGet,
    set: memcachedSet,
    remove: memcachedRemove,
};
const db = {
    get: mysqlGet,
    insert: mysqlInsert,
    update: mysqlUpdate,
    getMultiInfo: mysqlGetMultiInfo,
}

export { cache, db };