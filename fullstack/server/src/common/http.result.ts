import { HttpStatus } from '@nestjs/common';
import * as moment from 'moment';

export const StatusCode = HttpStatus

export const HttpMessage = {
  CONTINUE: 'CONTINUE',
  SWITCHING_PROTOCOLS: 'SWITCHING_PROTOCOLS',
  PROCESSING: 'PROCESSING',
  OK: 'OK',
  CREATED: 'CREATED',
  ACCEPTED: 'ACCEPTED',
  NON_AUTHORITATIVE_INFORMATION: 'NON_AUTHORITATIVE_INFORMATION',
  NO_CONTENT: 'NO_CONTENT',
  RESET_CONTENT: 'RESET_CONTENT',
  PARTIAL_CONTENT: 'PARTIAL_CONTENT',
  AMBIGUOUS: 'AMBIGUOUS',
  MOVED_PERMANENTLY: 'MOVED_PERMANENTLY',
  FOUND: 'FOUND',
  SEE_OTHER: 'SEE_OTHER',
  NOT_MODIFIED: 'NOT_MODIFIED',
  TEMPORARY_REDIRECT: 'TEMPORARY_REDIRECT',
  PERMANENT_REDIRECT: 'PERMANENT_REDIRECT',
  BAD_REQUEST: 'BAD_REQUEST',
  UNAUTHORIZED: 'UNAUTHORIZED',
  PAYMENT_REQUIRED: 'PAYMENT_REQUIRED',
  FORBIDDEN: 'FORBIDDEN',
  NOT_FOUND: 'NOT_FOUND',
  METHOD_NOT_ALLOWED: 'METHOD_NOT_ALLOWED',
  NOT_ACCEPTABLE: 'NOT_ACCEPTABLE',
  PROXY_AUTHENTICATION_REQUIRED: 'PROXY_AUTHENTICATION_REQUIRED',
  REQUEST_TIMEOUT: 'REQUEST_TIMEOUT',
  CONFLICT: 'CONFLICT',
  GONE: 'GONE',
  LENGTH_REQUIRED: 'LENGTH_REQUIRED',
  PRECONDITION_FAILED: 'PRECONDITION_FAILED',
  PAYLOAD_TOO_LARGE: 'PAYLOAD_TOO_LARGE',
  URI_TOO_LONG: 'URI_TOO_LONG',
  UNSUPPORTED_MEDIA_TYPE: 'UNSUPPORTED_MEDIA_TYPE',
  REQUESTED_RANGE_NOT_SATISFIABLE: 'REQUESTED_RANGE_NOT_SATISFIABLE',
  EXPECTATION_FAILED: 'EXPECTATION_FAILED',
  I_AM_A_TEAPOT: 'I_AM_A_TEAPOT',
  UNPROCESSABLE_ENTITY: 'UNPROCESSABLE_ENTITY',
  TOO_MANY_REQUESTS: 'TOO_MANY_REQUESTS',
  INTERNAL_SERVER_ERROR: 'INTERNAL_SERVER_ERROR',
  NOT_IMPLEMENTED: 'NOT_IMPLEMENTED',
  BAD_GATEWAY: 'BAD_GATEWAY',
  SERVICE_UNAVAILABLE: 'SERVICE_UNAVAILABLE',
  GATEWAY_TIMEOUT: 'GATEWAY_TIMEOUT',
  HTTP_VERSION_NOT_SUPPORTED: 'HTTP_VERSION_NOT_SUPPORTED',
}

export const parseJsonResult = data => {
  const res = { statusCode: HttpStatus.OK, message: '查询成功', data };
  if (!data) {
    res.statusCode = HttpStatus.NOT_FOUND;
    res.message = HttpMessage.NOT_FOUND;
  }
  return res;
};

export const parsePatchJsonResult = (data, address, res?) => {
  const result = { statusCode: HttpStatus.OK, message: '修改成功', data: address };
  if (!data) {
    result.statusCode = HttpStatus.FAILED_DEPENDENCY
    result.message = '修改失败'
  }
  if (res) {
    res.status(result.statusCode).json(result)
  }
  return result;
};

export const mappingData = (res, filter = [], transform = (data) => { }) => {
  if (Array.isArray(res)) {
    return res.map(v => Object.assign(v, {
      created_time: moment(v.created_time).format('YYYY-MM-DD HH:mm'),
      updated_time: moment(v.updated_time).format('YYYY-MM-DD HH:mm')
    }))
  } else {
    return !!res ? Object.assign(res, {
      created_time: moment(res.created_time).format('YYYY-MM-DD HH:mm'),
      updated_time: moment(res.updated_time).format('YYYY-MM-DD HH:mm')
    }) : res
  }
}