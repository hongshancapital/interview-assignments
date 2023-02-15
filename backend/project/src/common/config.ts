import * as yaml from 'yaml';
import { join } from 'path';
import { readFileSync } from 'fs';

type Config = {
  env: string;
  domain: string[];
  /**
   * 启动的端口号 default 3000
   */
  port: number;
  /**
   * 数据库配置
   */
  database: {
    type: string;
    url: string;
    synchronize: boolean;
  };

  /**
   * redis 配置
   */
  redis: {
    host: string;
    port: number;
    db: number;
  };
  /**
   * 短码配置
   */
  short: {
    /**
     * 字符集,默认是base58
     */
    alphabet: string;
    /**
     * 生成的短码默认长度
     */
    len: number;
    /**
     * 重试次数
     */
    retry: number;

    cache: {
      lru: {
        capacity: number;
      };
      redis: {
        // 过期时间
        expire: number;
      };
    };
  };
};

let defaultConfig: Config = {
  env: 'development',

  domain: ['http://127.0.0.1', '3000'],

  port: 3000,
  database: {
    type: 'mysql',
    url: 'mysql://127.0.0.1:3306/scc',
    synchronize: true,
  },
  redis: {
    host: '127.0.0.1',
    port: 6379,
    db: 0,
  },
  short: {
    alphabet: '123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz',
    retry: 3,
    len: 8,
    cache: {
      lru: {
        capacity: 1000,
      },
      redis: {
        expire: 60 * 60,
      },
    },
  },
};

// 合并默认配置
export const config: Config = Object.assign(
  defaultConfig,
  yaml.parse(readFileSync(join(__dirname, '../../config.yaml'), 'utf-8')),
);
