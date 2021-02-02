import { injectable, inject } from 'inversify';
import 'reflect-metadata';
import Interfaces from '../interfaces';
import { ID_Service_Intf, ID_Service_Response, ID_Service_Result_Code } from './id_service_intf';
import { ID_Service_Repository_Intf } from './id_service_repository_intf';
import { FALSY_ID } from '../constants';
import { RANGE_ID_MIN, RANGE_ID_MAX } from '../config';
import { logger } from '../logger';

@injectable()
export default class ID_Service_Impl implements ID_Service_Intf {
  private repository: ID_Service_Repository_Intf;

  constructor(@inject(Interfaces.ID_Service_Repository)repo: ID_Service_Repository_Intf) {
    this.repository = repo;
  }

  async request(range?: number): Promise<ID_Service_Response> {
    logger.debug(`ID_Service::request() called with range='${range}'`);
    try {
      let id = FALSY_ID;
      if (range !== undefined && range >= RANGE_ID_MIN && range <= RANGE_ID_MAX) {
        id = await this.repository.create_one_with_range(range);
      }

      if (id === FALSY_ID) { id = await this.repository.create_one_roughly(); }

      const res = (id === FALSY_ID) ? new ID_Service_Response(ID_Service_Result_Code.FAILURE, FALSY_ID, 'Failed')
        : new ID_Service_Response(ID_Service_Result_Code.SUCCESS, id, 'Succeeded');
      return res;
    } catch (err) {
      logger.error(`ID_Service::request() failed of '${err.message}'`);
      return new ID_Service_Response(ID_Service_Result_Code.EXCEPTION, FALSY_ID, 'Exception Happened');
    }
  }
}
