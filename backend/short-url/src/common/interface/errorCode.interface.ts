export enum ErrorCode {
  Sucess = 0,
  ParamError = 4001,
  GetLongUrlError = 5001,
  CreateShortUrlError = 5002,
}

export enum HttpStatus {
  OK = 200,
  AMBIGUOUS = 300,
  BAD_REQUEST = 400,
  INTERNAL_SERVER_ERROR = 500,
}