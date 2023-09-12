import { UrlModel } from "../models/url.model";
import { Url } from "../interfaces/url.interface";
import { bigintToBase62 } from "../utils/transfer";
import { Snowflake } from '../utils/snowflake';
import { container, singleton } from 'tsyringe';

@singleton()
export class UrlService {
  private snowflake: Snowflake; 

  constructor() {
    this.snowflake = new Snowflake(12n, 6n);
  }

  private getCode(snowflakeId: bigint): string {
    const code = bigintToBase62(snowflakeId);
    return code.substring(code.length - 8);
  }

  public async findByOption(url: string, userUid: string): Promise<Url | null> {

    return await UrlModel.findOne({ userUid: {$eq: userUid}, url: {$eq: url } });
  }


  public async findByCode(code: string): Promise<Url | null> {
    return await UrlModel.findOne({ code: {$eq: code } });
  }

  public async createByOption(url: string, userUid:string): Promise<Url> {
    const snowflakeId = this.snowflake.nextId();
    
    return await UrlModel.create({
      uid: `${snowflakeId}`,
      code: this.getCode(snowflakeId),
      userUid: userUid,
      url: url
    });
  }

}
const urlService = container.resolve(UrlService);
export default urlService;