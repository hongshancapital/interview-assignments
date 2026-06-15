import { memcachedGet, memcachedSet, memcachedRemove } from '../../../src/database/memcached';
import { mysqlGet, mysqlUpdate, mysqlInsert, mysqlGetMultiInfo } from '../../../src/database/mysql';
import { db, cache } from '../../../src/database';

describe('Unit tests of <database>', () => {
   describe('test cache', () => {
      test('cache.fetchOrigin is set correct.', async () => {
         expect(cache.get).toBe(memcachedGet);
      });

      test('cache.setOrigin is set correct.', async () => {
         expect(cache.set).toBe(memcachedSet);
      });

      test('cache.remove is set correct.', async () => {
         expect(cache.remove).toBe(memcachedRemove);
      });
   });
   describe('test db', () => {
      test('db.fetchOrign is set correct.', async () => {
         expect(db.get).toBe(mysqlGet);
      });
      test('db.insert is set correct.', async () => {
         expect(db.insert).toBe(mysqlInsert);
      });
      test('db.update is set correct.', async () => {
         expect(db.update).toBe(mysqlUpdate);
      });
      test('db.getMultiInfo is set correct.', async () => {
         expect(db.getMultiInfo).toBe(mysqlGetMultiInfo);
      });
   });
 });

