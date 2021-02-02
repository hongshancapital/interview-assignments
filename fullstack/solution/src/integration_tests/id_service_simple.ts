import { injectable } from 'inversify';
import 'reflect-metadata';
import { ID_Service_Intf, ID_Service_Response, ID_Service_Result_Code } from '../id_service/id_service_intf';
import { RANGE_ID_MIN, RANGE_ID_MAX, SEQUENCE_MAX } from '../config';
import { FALSY_ID } from '../constants';
import { logger } from '../logger';

@injectable()
export default class ID_Service_Simple implements ID_Service_Intf {
  private ranged_sequence : Map<number, number>;

  constructor() {
    logger.info('Simple ID Service: initializing.');
    this.ranged_sequence = new Map<number, number>();
    for (let i = 0; i < RANGE_ID_MAX; i += 1) { this.ranged_sequence.set(i, 0); }
    this.request.bind(this);
  }

  async request(range?: number): Promise<ID_Service_Response> {
    logger.debug(`Simple ID Service: request() called with preferred range='${range}'`);
    let range_id:number;
    if (range === undefined || range < RANGE_ID_MIN || range > RANGE_ID_MAX) {
      range_id = Math.floor(Math.random() * (RANGE_ID_MAX - RANGE_ID_MIN) + RANGE_ID_MIN);
    } else range_id = range;

    const sequence = this.ranged_sequence.get(range_id);
    if (sequence === undefined || sequence === SEQUENCE_MAX) { return new ID_Service_Response(ID_Service_Result_Code.EXCEPTION, FALSY_ID, 'Exception'); }

    this.ranged_sequence.set(range_id, sequence + 1);

    const id = range_id * (SEQUENCE_MAX + 1) + sequence;
    return new ID_Service_Response(ID_Service_Result_Code.SUCCESS, id, 'Succeeded');
  }
}
