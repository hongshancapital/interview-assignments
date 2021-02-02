import { Data_Storage_Intf } from './data_storage_intf';
import Data_Storage from './data_storage';
import { ID_MAX, ID_MIN } from '../config';
import { logger } from '../logger';

describe('Data Storage Unit Tests', () => {
  it('should save/remove a mapping with valid data successfully', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const res1 = await dy_storage.save_a_mapping(
        ID_MAX,
        'xxx.xxx.xxx',
        '0123456789ABCDEABCDE0123456789ABCDEABCDE',
      );
      expect(res1).toBeTruthy();
      const res2 = await dy_storage.remove_a_mapping(ID_MAX);
      expect(res2).toBeTruthy();
    } catch (err) {
      logger.debug(`Data_Storage UT #1: exception happened as '${err.message}'`);
    }
  });

  it('should not save a mapping with empty key', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const result = await dy_storage.save_a_mapping(
        -1,
        'xxx.xxx.xxx',
        '0123456789ABCDEABCDE0123456789ABCDEABCDE',
      );
      expect(result).toBeFalsy();
    } catch (err) {
      logger.debug(`Data_Storage UT #2: exception happened as '${err.message}'`);
    }
  });

  it('should query successfully after saving', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const res = await dy_storage.save_a_mapping(
        123,
        'www.abc.xxx',
        '1234567890ABCDEABCDE0123456789ABCDEABCDE',
      );
      expect(res).toBeTruthy();

      const res1 = await dy_storage.query_original_url_with(123);
      expect(res1).toEqual('www.abc.xxx');
      const res2 = await dy_storage.query_short_url_id_with('1234567890ABCDEABCDE0123456789ABCDEABCDE');
      expect(res2).toEqual(123);
    } catch (err) {
      logger.debug(`Data_Storage UT #3: exception happened as '${err.message}'`);
    } finally {
      await dy_storage.remove_a_mapping(123);
    }
  });

  it('should return \'undefined\' on queries if no findings', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const res1 = await dy_storage.query_original_url_with(ID_MIN);
      expect(res1).toBeUndefined();
      const res2 = await dy_storage.query_short_url_id_with('8f14243117531db9aaf6cc2c0b7c85e0fa9eb114');
      expect(res2).toBeUndefined();
    } catch (err) {
      logger.debug(`Data_Storage UT #4: exception happened as '${err.message}'`);
    }
  });

  it('should return \'undefined\' on queries with invalid querying keys', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const res1 = await dy_storage.query_original_url_with(-1);
      expect(res1).toBeUndefined();
      const res2 = await dy_storage.query_short_url_id_with('');
      expect(res2).toBeUndefined();
      const res3 = await dy_storage.query_short_url_id_with('A');
      expect(res3).toBeUndefined();
      const res4 = await dy_storage.query_short_url_id_with('1234567890ABCDEABCDE0123456789ABCDEABCDX');
      expect(res4).toBeUndefined();
    } catch (err) {
      logger.debug(`Data_Storage UT #5: exception happened as '${err.message}'`);
    }
  });

  it('should be OK even to remove a mapping with non-exist short_url_id', async () => {
    const dy_storage : Data_Storage_Intf = new Data_Storage();
    try {
      const res = await dy_storage.remove_a_mapping(321);
      expect(res).toBeTruthy();
    } catch (err) {
      logger.debug(`Data_Storage UT #6: exception happened as '${err.message}'`);
    }
  });
});
