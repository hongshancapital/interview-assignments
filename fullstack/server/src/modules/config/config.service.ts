import * as dotenv from 'dotenv';
import * as fs from 'fs';
import * as path from 'path';
import * as URLS from '../../baseUrl';
import * as Joi from 'joi'

// const Joi = require('joi');

export interface EnvConfig {
  [prop: string]: string;
}

export class ConfigService {
  private readonly envConfig: EnvConfig;
  private filePath: string;

  constructor(filePath?: string) {
    const config = dotenv.parse(
      fs.readFileSync(
        filePath ||
        path.resolve(__dirname, `../../env/${process.env.NODE_ENV || 'production'}${process.env.SERVER_ENV ? '.'+process.env.SERVER_ENV : ''}.env`),
      ),
    );
    this.envConfig = this.validateInput(config);
    this.filePath = filePath;
  }

  private validateInput(envConfig: EnvConfig): EnvConfig {
    const envVarsSchema = Joi.object({
      NODE_ENV: Joi.any()
        .valid('development', 'production', 'test', 'preview')
        .default('development'),
      SERVER_ENV: Joi.string(),

      PORT: Joi.number().default(3332), // 程序运行的端口号

      ORM_LOADING_PATH: Joi.string().required(), // TypeOrm 加载文件的根路径

      DATABASE_TYPE: Joi.string().default('mysql'), // 数据库类型
      DATABASE_HOST: Joi.string().default('localhost'), // 数据库主机地址
      DATABASE_PORT: Joi.number().default(3306),  // 数据库端口号
      DATABASE_USER: Joi.string().default('root'), // 数据库用户
      DATABASE_PWD: Joi.string(), // 数据库密码
      DATABASE_DB: Joi.string().required(), // 数据库名称
      DATABASE_SYNCHRONIZE: Joi.boolean().default(false), // 是否同步数据库表结构
      DATABASE_DROPSCHEMA: Joi.boolean().default(false), // 每次启动是否删除数据库表重新创建

      TOKEN_SECRET_KEY: Joi.string().default(URLS.SERVER_ENV),
      TOKEN_EXPIRES_IN: Joi.string().default('7d'),
      TOKEN_EXPIRES_IN_S: Joi.string().default('604800s'), // 1day -> '86400s'
      TOKEN_URL_DOMAIN: Joi.string().default(URLS.URL_DOMAIN),
      TOKEN_UUID: Joi.string().default(URLS.SERVER_ENV),
    });

    const { error, value: validatedEnvConfig } = envVarsSchema.validate(
      envConfig
    );
    if (error) {
      throw new Error(`Config validation error: ${error.message}`);
    }
    return validatedEnvConfig;
  }

  get(key: string) {
    return this.envConfig[key];
  }

  getAll() {
    return this.envConfig;
  }

  save(data) {
    fs.writeFile(
      this.filePath || path.resolve(__dirname, `../env/${process.env.NODE_ENV || 'production'}${process.env.SERVER_ENV ? '.'+process.env.SERVER_ENV : ''}.env`),
      Object.keys(data).map(v => `${v}=${data[v]}`).join('\n'),
      'utf8',
      (res) => {
        // console.log('res', res);
      }
    );
  }
}
