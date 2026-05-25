/**
 * 短域名存储接口：接受长域名信息，返回短域名信息
 */

import { BLogicContext } from "../../common/base";
import { BaseBLogic } from "../base-blogic";
import { BaseModel } from "../../model/base-model";
import { getShortUrl } from "../../utils/common";

export class ShortUrlSaveBLogic extends BaseBLogic {
  public async doExecute(context: BLogicContext<any>) {
    const { url } = context.input;
    const baseModel: BaseModel = new BaseModel(context.proxy);
    const key: string = getShortUrl();
    const res: string = await baseModel.set(key, url);
    if (res && res == "OK") {
      return {
        url: key,
      };
    }
  }
}
