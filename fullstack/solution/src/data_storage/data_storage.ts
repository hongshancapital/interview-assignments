import { injectable } from 'inversify';
import 'reflect-metadata';
import * as dynamoose from 'dynamoose';
import { Data_Storage_Intf } from './data_storage_intf';
import {
  AWS_SDK_ACCESSKEYID, AWS_SDK_SECRET, AWS_SDK_REGION, AWS_DYNAMODB_TABLE,
  ID_MAX, ID_MIN,
} from '../config';
import { logger } from '../logger';

dynamoose.aws.sdk.config.update({
  accessKeyId: AWS_SDK_ACCESSKEYID,
  secretAccessKey: AWS_SDK_SECRET,
  region: AWS_SDK_REGION,
});
dynamoose.aws.ddb.local();

@injectable()
export default class Data_Storage implements Data_Storage_Intf {
  // eslint-disable-next-line
  private Model : any;

  static schema = new dynamoose.Schema({
    short_url_id: {
      hashKey: true,
      type: Number,
      validate: (v) => (v >= ID_MIN) && (v <= ID_MAX),
    },
    original_url: String,
    original_url_chksum: {
      type: String,
      index: {
        name: 'reverse_index',
        global: true,
      },
    },
  });

  constructor() {
    logger.info('Data_Storage: initializing');
    this.Model = dynamoose.model(
      <string>AWS_DYNAMODB_TABLE,
      Data_Storage.schema,
    );
    logger.info('Data_Storage: initialized');
  }

  async save_a_mapping(short_url_id:number,
    original_url:string,
    original_url_check_sum:string): Promise<boolean> {
    logger.debug(`Data_Storage::save_a_mapping() called with '{${short_url_id}}'`);
    const mapping = new this.Model({
      short_url_id,
      original_url,
      original_url_chksum: original_url_check_sum,
    });
    try {
      await mapping.save();
      return true;
    } catch (err) {
      logger.error(`Data_Storage: save_a_mapping() failed of '${err.message}'`);
      return false;
    }
  }

  async query_short_url_id_with(check_sum:string): Promise<number|undefined> {
    logger.debug(`Data_Storage::query_short_url_id_with() called with '${check_sum}'`);
    try {
      const results = await this.Model.query('original_url_chksum').eq(check_sum).exec();
      return (results.count > 0) ? results[0].toJSON().short_url_id : undefined;
    } catch (err) {
      logger.error(`Data_Storage: query_short_url_id_with() failed of '${err.message}'`);
      return undefined;
    }
  }

  async query_original_url_with(short_url_id:number) : Promise<string|undefined> {
    logger.debug(`Data_Storage::query_original_url_with() called with '${short_url_id}'`);
    try {
      const result = await this.Model.get(short_url_id);
      return (result === undefined) ? undefined : result.toJSON().original_url;
    } catch (err) {
      logger.error(`Data_Storage: query_original_url_with() failed of '${err.message}'`);
      return undefined;
    }
  }

  async remove_a_mapping(short_url_id:number): Promise<boolean> {
    logger.debug(`Data_Storage::remove_a_mapping() called with '${short_url_id}'`);
    try {
      await this.Model.delete(short_url_id);
      return true;
    } catch (err) {
      logger.error(`Data_Storage: remove_a_mapping() failed of '${err.message}'`);
      return false;
    }
  }

  clear_data():void {
    // this is left for mocking class to restore the data in each round, should not enter here.
    logger.debug('Data_Storage::clear_data() called');
  }
}
