import BaseModel from './BaseModel';

export default class ShortUrlModel extends BaseModel {
  public app_id!: string;
  public short_code!: string;
  public origin_url!: string;
  public static tableName = 'short_url';
  public static modelPaths = [__dirname];
}
