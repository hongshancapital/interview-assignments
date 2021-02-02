import { injectable } from 'inversify';
import 'reflect-metadata';
import { Data_Storage_Intf } from '../data_storage_intf';
import { logger } from '../../logger';

@injectable()
export default class Data_Storage implements Data_Storage_Intf {
  private hash_map1;

  private hash_map2;

  private hash_map3;

  constructor() {
    logger.info('Mocked Data Storage: initializing...');
    this.hash_map1 = new Map<number, string>(); // short_url_id to original_url
    this.hash_map2 = new Map<string, number>(); // check_sum to short_url_id
    this.hash_map3 = new Map<number, string>(); // short_url_id to check_sum
    logger.info('Mocked Data Storage: initialized.');
  }

  async save_a_mapping(short_url_id:number,
    original_url:string,
    original_url_check_sum:string): Promise<boolean> {
    logger.debug('Mocked Data Storage: save_a_mapping() called');
    this.hash_map1.set(short_url_id, original_url);
    this.hash_map2.set(original_url_check_sum, short_url_id);
    this.hash_map3.set(short_url_id, original_url_check_sum);
    return true;
  }

  async query_short_url_id_with(check_sum:string): Promise<number|undefined> {
    logger.debug('Mocked Data Storage: query_short_url_id_with () called');
    return this.hash_map2.get(check_sum);
  }

  async query_original_url_with(short_url_id:number) : Promise<string|undefined> {
    logger.debug('Mocked Data Storage: query_original_url_with () called');
    return this.hash_map1.get(short_url_id);
  }

  async remove_a_mapping(short_url_id:number): Promise<boolean> {
    logger.debug('Mocked Data Storage: remove_a_mapping () called');
    const check_sum = this.hash_map3.get(short_url_id);
    this.hash_map2.delete(check_sum);
    this.hash_map1.delete(short_url_id);
    this.hash_map3.delete(short_url_id);
    return true;
  }

  clear_data(): void {
    logger.info('Mocked Data Storage: clear_data() called.');
    this.hash_map1.clear();
    this.hash_map2.clear();
    this.hash_map3.clear();
  }
}
