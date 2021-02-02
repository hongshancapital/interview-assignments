import { DB_Provider_Intf } from '../db_provider_intf';
import { logger } from '../../logger';

export default class DB_Provider_MySQL implements DB_Provider_Intf {
  constructor() { logger.info('Mocked MySQL DB Provider: initialized'); }

  async fetch_sequence_of_a_range(dummy_range:number):Promise<number> { return Promise.resolve(0); }

  async increase_sequence_of_a_range(dummy_range:number, dummy_sequence:number):Promise<boolean> {
    return Promise.resolve(true);
  }

  async select_a_capable_range_randomly():Promise<number> { return Promise.resolve(0); }

  async select_a_range_and_increase_its_sequence_transactionally():Promise<number[]> {
    return Promise.resolve([0, 0]);
  }

  stop():void { logger.info('Mocked MySQL DB Provider: stoped'); }
}
