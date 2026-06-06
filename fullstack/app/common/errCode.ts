export const SUCCESS: string = '0';
export const PARAM_ERROR: string = '1001';

export const HASH_ERROR: string = '3001';
export const BASE62_ERROR: string = '3002';

export const DB_RUNTIME_ERROR: string = '5001';
export const NOT_FOUND: string = '5002';

export const DEFAULT_CODE: string = '9001';

interface ErrorMsgMap {
  [ errorCode: string ] : string
}

export const errMsgMap: ErrorMsgMap = {
  [PARAM_ERROR]: '输入参数错误',

  [HASH_ERROR]: 'hash生成错误',

  [DB_RUNTIME_ERROR]: '数据库运行错误',
  [NOT_FOUND]: '未查询到对应数据',

  [DEFAULT_CODE]: '系统内部错误'
};