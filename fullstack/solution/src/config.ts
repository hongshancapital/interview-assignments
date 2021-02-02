import * as dotenv from 'dotenv';
import {
  RANGE_ID_LEN_DEFAULT,
  RANGE_ID_MAX_DEFAULT,
  SEQUENCE_LEN_DEFAULT,
  SEQUENCE_MAX_DEFAULT,

  CACHE_CAPACITY_DEFAULT,
} from './constants';

let path;
switch (process.env.NODE_ENV) {
  case 'test':
    path = `${__dirname}/.env.test`;
    break;
  case 'prod':
    path = `${__dirname}/.env.prod`;
    break;
  default:
    path = `${__dirname}/.env.dev`;
}
dotenv.config({ path });

export const {
  LOG_LEVEL,
  MYSQL_HOST,
  MYSQL_USER,
  MYSQL_PWD,
  MYSQL_DBNAME,
  AWS_SDK_ACCESSKEYID,
  AWS_SDK_SECRET,
  AWS_SDK_REGION,
  AWS_DYNAMODB_TABLE,
} = process.env;

if (AWS_DYNAMODB_TABLE === undefined) {
  throw new Error('Error [Fatal] : DyanmoDB Table Name Not Set');
}

export const ID_MIN = 0;
export const RANGE_ID_MIN = 0;
export const SEQUENCE_MIN = 0;
export const RANGE_ID_LEN = (process.env.RANGE_ID_LEN !== undefined)
  ? parseInt(process.env.RANGE_ID_LEN) : RANGE_ID_LEN_DEFAULT;
export const RANGE_ID_MAX = (process.env.RANGE_ID_MAX !== undefined)
  ? parseInt(process.env.RANGE_ID_MAX) : RANGE_ID_MAX_DEFAULT;
export const SEQUENCE_LEN = (process.env.SEQUENCE_LEN !== undefined)
  ? parseInt(process.env.SEQUENCE_LEN) : SEQUENCE_LEN_DEFAULT;
export const SEQUENCE_MAX = (process.env.SEQUENCE_MAX !== undefined)
  ? parseInt(process.env.SEQUENCE_MAX) : SEQUENCE_MAX_DEFAULT;
export const ID_LEN = RANGE_ID_LEN + SEQUENCE_LEN;
export const ID_MAX = RANGE_ID_MAX * (SEQUENCE_MAX + 1) + SEQUENCE_MAX - 1;

export const CACHE_CAPACITY = (process.env.CACHE_CAPACITY !== undefined)
  ? parseInt(process.env.CACHE_CAPACITY) : CACHE_CAPACITY_DEFAULT;

export const SHORT_URL_PREFIX = (process.env.SHORT_URL_PREFIX !== undefined)
  ? process.env.SHORT_URL_PREFIX : '';
export const SERVICE_PORT = (process.env.SERVICE_PORT !== undefined)
  ? parseInt(process.env.SERVICE_PORT) : 3000;
export const SERVICE_ENDPOINT = (process.env.SERVICE_ENDPOINT !== undefined)
  ? process.env.SERVICE_ENDPOINT : '';

export const LOG_FILENAME = `${__dirname}/logs/${process.env.LOG_FILENAME}`;
