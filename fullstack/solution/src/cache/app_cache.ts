import { injectable } from 'inversify';
import 'reflect-metadata';
import * as LRUCache from 'lru-cache';
import { App_Cache_Intf } from './app_cache_intf';
import { CACHE_CAPACITY } from '../config';
import { logger } from '../logger';

@injectable()
export default class App_Cache implements App_Cache_Intf {
  private cache1: LRUCache <string, number>;

  private cache2: LRUCache <number, string>;

  constructor() {
    this.cache1 = new LRUCache(CACHE_CAPACITY);
    this.cache2 = new LRUCache(CACHE_CAPACITY);
    logger.info('Cache Service Start');
  }

  save_check_sum_and_short_url_id(check_sum:string, short_url_id:number):void {
    logger.debug(`App_Cache::save_check_sum_and_short_url_id() called with ('${check_sum}', ${short_url_id})`);
    this.cache1.set(check_sum, short_url_id);
  }

  save_short_url_id_and_original_url(short_url_id:number, original_url:string):void {
    logger.debug(`App_Cache::save_short_url_id_and_short_url_id() called with (${short_url_id}, ...)`);
    this.cache2.set(short_url_id, original_url);
  }

  retrieve_short_url_id_with(check_sum:string):number|undefined {
    logger.debug(`App_Cache::retrieve_short_url_id_with() called with '${check_sum}'`);
    const res = this.cache1.get(check_sum);
    return res;
  }

  retrieve_original_url_with(short_url_id:number): string|undefined {
    logger.debug(`App_Cache::retrieve_original_url_with() called with '${short_url_id}'`);
    const res = this.cache2.get(short_url_id);
    return res;
  }
}
