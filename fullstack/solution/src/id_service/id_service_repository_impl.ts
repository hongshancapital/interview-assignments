import { injectable, inject } from 'inversify';
import 'reflect-metadata';
import Interfaces from '../interfaces';
import { ID_Service_Repository_Intf } from './id_service_repository_intf';
import { DB_Provider_Intf } from './db_provider_intf';
import { SEQUENCE_MAX } from '../config';
import { FALSY_ID, FALSY_SEQUENCE, FALSY_RANGE_ID } from '../constants';
import { logger } from '../logger';

function make_id(range_id:number, sequence:number):number {
  return range_id * (SEQUENCE_MAX + 1) + sequence;
}

@injectable()
export default class ID_Service_Repository_Impl implements ID_Service_Repository_Intf {
  private db: DB_Provider_Intf;

  constructor(@inject(Interfaces.DB_Provider)db:DB_Provider_Intf) {
    this.db = db;
  }

  async create_one_with_range(range_id:number): Promise<number> {
    logger.debug(`ID_Service_Repository_Impl::create_one_with_range() called with range_id='${range_id}'`);
    const sequence = await this.db.fetch_sequence_of_a_range(range_id);
    if (sequence === FALSY_SEQUENCE) { return FALSY_ID; }

    const result = await this.db.increase_sequence_of_a_range(range_id, sequence);
    return result ? make_id(range_id, sequence) : FALSY_ID;
  }

  async create_one_roughly(): Promise<number> {
    logger.debug('ID_Service_Repository_Impl::create_one_roughtly() called');
    logger.debug('ID_Service_Repository_Impl::create_one_roughtly() to try finding a capable range');
    const range_id = await this.db.select_a_capable_range_randomly();
    if (range_id === FALSY_RANGE_ID) { return FALSY_ID; } // cannot find any range available

    logger.debug(`ID_Service_Repository_Impl::create_one_roughtly() to try with an optimistic lock on range '${range_id}'`);
    const res = await this.create_one_with_range(range_id);
    if (res !== FALSY_ID) { return res; } // successfully proceed with optimistic locking

    logger.debug('ID_Service_Repository_Impl::create_one_roughtly() to try with an transaction');
    const result = await this.db.select_a_range_and_increase_its_sequence_transactionally();
    return (result[0] === FALSY_RANGE_ID || result[1] === FALSY_SEQUENCE)
      ? FALSY_ID : make_id(result[0], result[1]);
  }
}
