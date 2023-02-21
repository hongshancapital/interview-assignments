import baseApiUrl, { defaultApiVersion } from "../config";

interface IApi {
  host: string;
  version: string;
  getApi: (routeName: string) => string;
}

/**
 * API地址类，用于获取请求的API地址
*/
class Api implements IApi {
  host: string;
  version: string;
  constructor(host: string, version?: string) {
    this.host = host;
    this.version = version || defaultApiVersion;
  }

  getApi(routeName: string) {
    return `${this.host}/api/${this.version}/${routeName}`;
  }
}

const apiRoute: Api = Object.freeze(new Api(baseApiUrl));

export { apiRoute };
