export const ENCODE_BASE = 64;
export const BASE64CHARSET = Array.from('4UH7+o0r9K5=2Al6S31Z8zQajbeWfPgcYknpOvsxwCdDiEBTFyGuIqJLtMNmRVXh');

export const CHECK_SUM_LENGTH = 40;
export const SHORT_URL_TOKEN_LENGTH = 8;

export const RANGE_ID_LEN_DEFAULT = 2; // length in byte
export const RANGE_ID_MAX_DEFAULT = 0xffff; // 2 power 16
export const SEQUENCE_LEN_DEFAULT = 4; // length in byte
export const SEQUENCE_MAX_DEFAULT = 0xffffffff; // 2 power 32

export const FALSY_ID = -100;
export const FALSY_RANGE_ID = -10;
export const FALSY_SEQUENCE = -1;

export const CACHE_CAPACITY_DEFAULT = 16 * 1024 * 1024;

export const STATUS_CODE_OK = 200;
export const STATUS_CODE_CREATED = 201;
export const STATUS_CODE_BAD_REQUEST = 400;
export const STATUS_CODE_NOT_FOUND = 404;
export const STATUS_CODE_INTERNAL_SERVER_ERROR = 500;
