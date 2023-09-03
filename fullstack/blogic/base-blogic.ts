import { BLogicContext } from "../common/base";

export abstract class BaseBLogic {
  abstract doExecute(context: BLogicContext<any>): any;
  needAuth: boolean = false;
  needLogger: boolean = false;

  public async execute(context: BLogicContext<any>) {
    return await this.doExecute(context);
  };
}


