import { injectable, inject } from 'inversify';
import 'reflect-metadata';
import { Request, Response } from 'express';
import Interfaces from '../interfaces';
import {
  build_short_url_from,
  build_short_url_id_from,
  calculate_check_sum,
  make_range_id_from,
} from './utils';
import {
  FALSY_ID,
  STATUS_CODE_OK,
  STATUS_CODE_CREATED,
  STATUS_CODE_NOT_FOUND,
  STATUS_CODE_INTERNAL_SERVER_ERROR,
} from '../constants';
import { SHORT_URL_PREFIX, RANGE_ID_LEN } from '../config';
import { App_Cache_Intf } from '../cache/app_cache_intf';
import { ID_Service_Intf, ID_Service_Result_Code } from '../id_service/id_service_intf';
import { Data_Storage_Intf } from '../data_storage/data_storage_intf';
import { logger } from '../logger';

@injectable()
export default class Controller {
  private cache: App_Cache_Intf;

  private id_service: ID_Service_Intf;

  private storage: Data_Storage_Intf;

  constructor(@inject(Interfaces.Cache) cache: App_Cache_Intf,
    @inject(Interfaces.Storage) storage: Data_Storage_Intf,
    @inject(Interfaces.ID_Service) id_service:ID_Service_Intf) {
    this.cache = cache;
    this.storage = storage;
    this.id_service = id_service;

    this.create_short_url.bind(this);
    this.resolve_short_url.bind(this);
  }

  async retrieve_short_url_id_with(check_sum:string) : Promise<number> {
    let id = this.cache.retrieve_short_url_id_with(check_sum);
    if (id === undefined) {
      id = await this.storage.query_short_url_id_with(check_sum);
      if (id !== undefined) { this.cache.save_check_sum_and_short_url_id(check_sum, id); }
    }
    return (id === undefined) ? FALSY_ID : id;
  }

  async make_short_url_id_with(original_url:string, check_sum:string) : Promise <number> {
    const range_id = make_range_id_from(check_sum, RANGE_ID_LEN);
    const response = await this.id_service.request(range_id);

    if (response.code() !== ID_Service_Result_Code.SUCCESS) { return FALSY_ID; }

    const id = response.value();
    const res = await this.storage.save_a_mapping(id, original_url, check_sum);
    return res ? id : FALSY_ID;
  }

  async create_short_url(req:Request, res:Response): Promise<void> {
    const { original_url } = req.body;
    logger.debug(`Controller::create_short_url() called with: '${original_url}'`);

    const check_sum = calculate_check_sum(original_url);
    let id = await this.retrieve_short_url_id_with(check_sum);
    if (id !== FALSY_ID) {
      res.status(STATUS_CODE_OK)
        .json({ short_url: build_short_url_from(id, SHORT_URL_PREFIX), original_url });
    } else {
      id = await this.make_short_url_id_with(original_url, check_sum);
      if (id === FALSY_ID) {
        res.status(STATUS_CODE_INTERNAL_SERVER_ERROR).send('Internal Server Error');
      } else {
        res.status(STATUS_CODE_CREATED)
          .json({ short_url: build_short_url_from(id, SHORT_URL_PREFIX), original_url });
        this.cache.save_check_sum_and_short_url_id(check_sum, id);
      }
    }
  }

  async retrieve_original_url_with(short_url:string):Promise<string> {
    const short_url_id = build_short_url_id_from(short_url, SHORT_URL_PREFIX);
    let original_url = this.cache.retrieve_original_url_with(short_url_id);
    if (original_url === undefined) {
      original_url = await this.storage.query_original_url_with(short_url_id);
      if (original_url !== undefined) {
        this.cache.save_short_url_id_and_original_url(short_url_id, original_url);
      }
    }
    return (original_url === undefined) ? '' : original_url;
  }

  async resolve_short_url(req:Request, res:Response): Promise<void> {
    const { short_url } = req.params;
    logger.debug(`Controller::resolve_short_url() called with: '${short_url}''`);

    const original_url = await this.retrieve_original_url_with(short_url);
    if (original_url !== '') { res.status(STATUS_CODE_OK).json({ short_url, original_url }); } else { res.status(STATUS_CODE_NOT_FOUND).send('Not Found'); }
  }
}
