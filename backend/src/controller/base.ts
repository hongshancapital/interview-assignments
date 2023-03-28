import {Application, IContext} from "../types/common";
import { baseConfig, BaseConfig } from "../config";
export default class BaseController {
    public ctx: IContext;
    public app: Application;
    public config: BaseConfig;
    constructor(ctx: IContext) {
        this.ctx = ctx;
        this.app = ctx.app;
        this.config = baseConfig;
    }
}
