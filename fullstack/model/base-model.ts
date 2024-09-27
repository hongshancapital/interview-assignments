import { ErrorConst } from "../common/exception/exception-const";
import { SystemException } from "../common/exception/system-exception";
import { DBProxy } from "../common/db-proxy";
import { logger } from "../common/logger";

export class BaseModel {
  constructor(private dbproxy: DBProxy) {}
  async set(key: string, value: string) {
    let r: string = await this.dbproxy.set(key, value);
    return r;
  }

  async get(key: string) {
    let r: string = await this.dbproxy.get(key);
    return r;
  }
}
