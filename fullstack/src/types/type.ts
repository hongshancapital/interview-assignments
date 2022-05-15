export interface MySQLConfig {
  host: string;
  port: number;
  user: string;
  database: string;
  pass: string;
  waitForConnections?: boolean;
  connectionLimit?: number;
}

export interface RedisConfig {
  host?: string;
  port?: number;
  username?: string;
  password?: string;
}

export interface GlobalConfig {
  redis: RedisConfig;
  mysql: MySQLConfig;
}

export interface URLRecord {
  id: number,
  long_url: string
}

export interface BaseRes<T> {
  code: number;
  msg: string;
  data: T;
}

export interface LongURLRes {
  longURL: string;
}
