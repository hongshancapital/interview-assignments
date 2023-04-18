export type HttpErrorStatusCode = 400 | 401 | 403 | 404 | 409 | 500 | 503;
export enum HttpErrorStatusCodeEnum {
  BadData = 400,
  NotAuthorized = 401,
  Forbidden = 403,
  NotFound = 404,
  Conflict = 409,
  InternalServerError = 500,
  ServiceUnavailable = 503
}

type ErrorMessagesType = {
  [code in HttpErrorStatusCode]: string
};

export const errorMessages: ErrorMessagesType = {
  400: 'Bad Data',
  401: 'Not Authorized',
  403: 'Forbidden',
  404: 'Not Found',
  409: 'Conflict',
  500: 'Internal Server Error',
  503: 'Service Unavailable',
};

export enum HttpErrorCode {
  systemError = 'SV00001',
  typedParamInvalid = 'SV00002',
  bodyTypeInvalid = 'SV00003',
  queryTypeInvalid = 'SV00004',
  outputTypeInvalid = 'SV00005',

  genShortDNSFailed = 'SV01001',
  getLongDNSFailed = 'SV01002',
}
