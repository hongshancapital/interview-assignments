import { ID_Service_Repository_Intf } from '../ID_Service_Repository_Intf';
import { DB_Provider_Intf } from '../DB_Provider_Intf';
import { logger } from '../../logger';

export default class ID_Service_Repository_Impl implements ID_Service_Repository_Intf {
  constructor(private dummy:DB_Provider_Intf) {
    logger.info('Mocked ID Service Repository: initialized');
  }

  async create_one_with_range(range_id: number):Promise<number> {
    logger.debug(`Mocked ID Service Repository: create_one_with_range() called with range_id = '${range_id}'`);
    return Promise.resolve(1);
  }

  async create_one_roughly():Promise<number> {
    logger.debug('Mocked ID Service Repository: create_one_roughly() called');
    return Promise.resolve(1);
  }
}
