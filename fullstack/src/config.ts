import * as path from 'path'
import * as fs from 'fs'
import * as beautify from 'js-beautify'

const configPath: string = path.resolve(__dirname, '../.config')

interface Database {
  connectionLimit?: number;
  host: string;
  port: number;
  user: string;
  password: string;
  database: string;
}
interface RedisConfig {
  ip: string;
  port: number;
  password?: string;
}
interface ServerConfig{
  port: number;
  database: Database;
  prefix: {
    api: string;
  };
  redis: {
    default: RedisConfig;
    pooSize: number;
    maxPoolSize: number;
  };
  log: {
    level: string;
    appenders: string[];
  };
}

let globalConfig: ServerConfig = {
  port: 3000,
  prefix: {
    api: "/api",
  },
  database: {
    host: "127.0.0.1",
    port: 3306,
    user: "root",
    password: "Dong_1013501639",
    database: "test",
    connectionLimit: 20
  },
  redis: {
    pooSize: 10,
    maxPoolSize: 100,
    default: {
      ip: '127.0.0.1',
      port: 6379,
      password: ''
    }
  },
  log: {
    level: 'error',
    appenders: ['file']
  }
}
try {
  fs.statSync(configPath)
  globalConfig = JSON.parse(fs.readFileSync(configPath).toString())
} catch (e) {
  fs.writeFileSync(configPath, beautify.js(JSON.stringify(globalConfig), {
    'indent_size': 2
  }))
}
export default globalConfig