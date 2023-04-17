import "reflect-metadata";
import { container, singleton } from 'tsyringe';

import _ from "lodash";

import developmentConfig from "./development";
import testingConfig from "./testing";
import productionConfig from "./production";
import stagingConfig from "./staging";

const configs = {
  development: developmentConfig,
  testing: testingConfig,
  production: productionConfig,
  staging: stagingConfig,
};
const env = process.env.NODE_ENV?.toLowerCase() || "development";

@singleton()
export class Config {
  env = env;
  cache: { [k: string]: any } = {};

  constructor() {
    Object.assign(this.cache, { ...(configs as any)[env] });
  }

  isProduction() {
    return this.env == 'production';
  }

  get(key: string): string | any {
    return _.get(this.cache, key);
  }
}

export const configService = container.resolve(Config);

export default configService;
