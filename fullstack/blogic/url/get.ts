/**
 * 短域名读取接口：接受短域名信息，返回长域名信息。
 */

import { BLogicContext } from "../../common/base";
import { BaseModel } from "../../model/base-model";
import { BaseBLogic } from "../base-blogic";

export class ShortUrlGetBLogic extends BaseBLogic {
  public async doExecute(context: BLogicContext<any>) {
    const { url } = context.input;
    const baseModel: BaseModel = new BaseModel(context.proxy);
    const res: string = await baseModel.get(url);
    if (res) {
      return {
        url: res,
      };
    }
  }
}
