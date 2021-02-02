import App_Cache from './app_cache';
import { ID_MAX, ID_MIN } from '../config';

describe('App Cache Unit Tests', () => {
  it('should return \'undefined\' if not hit', () => {
    const cache = new App_Cache();
    const res1 = cache.retrieve_original_url_with(ID_MAX);
    expect(res1).toBeUndefined();
    const res2 = cache.retrieve_short_url_id_with('01234567890123456789');
    expect(res2).toBeUndefined();
  });
  it('should hit when queried with check_sum after saving', () => {
    const cache = new App_Cache();
    cache.save_check_sum_and_short_url_id('ABCDEFGHIJ0123456789', ID_MAX);
    const res = cache.retrieve_short_url_id_with('ABCDEFGHIJ0123456789');
    expect(res).toEqual(ID_MAX);
  });
  it('should hit when queried with short_url_id after saving', () => {
    const cache = new App_Cache();
    cache.save_short_url_id_and_original_url(ID_MIN, 'xxx.xxx.xxx');
    const res = cache.retrieve_original_url_with(ID_MIN);
    expect(res).toEqual('xxx.xxx.xxx');
  });
});
