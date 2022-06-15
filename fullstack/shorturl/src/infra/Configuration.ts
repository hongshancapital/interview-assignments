import dotenv from 'dotenv';

import { DotenvExpandOptions, expand } from 'dotenv-expand';

import fse from 'fs-extra';

import path from 'path';

import { get } from 'lodash';

type KeyOf<T> = keyof T extends never ? string : keyof T;

export function isUndefined(obj: any): obj is undefined {
  return typeof obj === 'undefined';
}

export function isNil(val: any): val is null | undefined {
  return isUndefined(val) || val === null;
}

export function isObject(fn: any): fn is object {
  return !isNil(fn) && typeof fn === 'object';
}

export interface ConfigurationOptions {
  ignoreEnvVars?: boolean;
  ignoreEnvFile?: boolean;
  envFilePath?: string | string[];
  expandVariables?: boolean | DotenvExpandOptions;
}

export default class Configuration {
  private static loadEnvFile(options: ConfigurationOptions): Record<string, any> {
    const envFilePaths = Array.isArray(options.envFilePath)
      ? options.envFilePath
      : [options.envFilePath || path.resolve(process.cwd(), '.env')];

    let config: ReturnType<typeof dotenv.parse> = {};

    for (const envFilePath of envFilePaths) {
      if (fse.existsSync(envFilePath)) {
        config = Object.assign(dotenv.parse(fse.readFileSync(envFilePath)), config);

        if (options.expandVariables) {
          const expandOptions: DotenvExpandOptions = typeof options.expandVariables === 'object' ? options.expandVariables : {};
          config = expand({ ...expandOptions, parsed: config }).parsed || config;
        }
      }
    }

    return config;
  }

  private static assignVariablesToProcess(config: Record<string, any>) {
    if (!isObject(config)) return;
    const entries = Object.entries(config).filter(([key]) => !(key in process.env));
    entries.forEach(([key, value]) => (process.env[key] = value),);
  }

  public static init(options: ConfigurationOptions) {
    let config = options.ignoreEnvFile ? {} : this.loadEnvFile(options);
    if (!options.ignoreEnvVars) Object.assign(config, { ...process.env });
    this.assignVariablesToProcess(config);
  }

  public static get<T = any>(propertyPath: string, defaultValue?: T): T | undefined {
    return get(process.env as Record<string, any>, propertyPath, defaultValue);
  }

  public static getOrThrow<T = any>(propertyPath: string, defaultValue?: T): Exclude<T, undefined> {
    const value = this.get(propertyPath, defaultValue) as | T | undefined;

    if (isUndefined(value)) {
      throw new TypeError(`Configuration key "${propertyPath.toString()}" does not exist`);
    }

    return value as Exclude<T, undefined>;
  }
}