import { mockRequest, mockResponse } from 'jest-mock-req-res';
import Validator from './validator';
import { STATUS_CODE_BAD_REQUEST } from '../constants';
import { SHORT_URL_PREFIX } from '../config';

const next = jest.fn();
beforeEach(() => jest.clearAllMocks());
describe('Validator::on_create_request() Unit Test', () => {
  it('should call the next function if the url is valid', () => {
    const valid_body_sample = { original_url: 'https://www.vim.org' };
    const request = mockRequest({ body: valid_body_sample });
    const response = mockResponse();
    Validator.on_create_request(request, response, next);
    expect(response.status).not.toHaveBeenCalled();
    expect(next).toHaveBeenCalledTimes(1);
  });
  it('should return BAD_REQUEST to if the url is a short url already', () => {
    const request = mockRequest({ body: { original_url: `${SHORT_URL_PREFIX}A23XYZ78` } });
    const response = mockResponse();
    Validator.on_create_request(request, response, next);
    expect(response.status).toHaveBeenCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_BAD_REQUEST);
    expect(next).toHaveBeenCalledTimes(0);
  });

  it('should return BAD_REQUEST to if the original url is empty', () => {
    const request = mockRequest({ body: { original_url: '' } });
    const response = mockResponse();
    Validator.on_create_request(request, response, next);
    expect(response.status).toHaveBeenCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_BAD_REQUEST);
    expect(next).toHaveBeenCalledTimes(0);
  });

  it('should return BAD_REQUEST to if the original url is not provided', () => {
    const request = mockRequest({ body: { original: '' } });
    const response = mockResponse();
    Validator.on_create_request(request, response, next);
    expect(response.status).toHaveBeenCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_BAD_REQUEST);
    expect(next).toHaveBeenCalledTimes(0);
  });

  it('should return BAD_REQUEST to if the original url is not a valid http url', () => {
    const request = mockRequest({ body: { original_url: 'ftp://www.download.com' } });
    const response = mockResponse();
    Validator.on_create_request(request, response, next);
    expect(response.status).toHaveBeenCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_BAD_REQUEST);
    expect(next).toHaveBeenCalledTimes(0);
  });
});

describe('Validator::on_resolve_request() Test', () => {
  it('should call the next function if the url is valid', () => {
    const valid_params_sample = { short_url: `${SHORT_URL_PREFIX}ABCDEFGH` };
    const request = mockRequest({ params: valid_params_sample });
    const response = mockResponse();
    Validator.on_resolve_request(request, response, next);
    expect(response.status).not.toHaveBeenCalled();
    expect(next).toHaveBeenCalledTimes(1);
  });
  it('should return BAD_REQUEST when requested to resolve invalid short url', () => {
    const res = mockResponse();
    const req1 = mockRequest({ params: { short_url: 'https://www.google.com' } });
    Validator.on_resolve_request(req1, res, next);
    expect(res.status).toHaveBeenCalledTimes(1);
    expect(res.status).lastCalledWith(STATUS_CODE_BAD_REQUEST);

    const req2 = mockRequest({ params: { short_url: '' } });
    Validator.on_resolve_request(req2, res, next);
    expect(res.status).toHaveBeenCalledTimes(2);
    expect(res.status).lastCalledWith(STATUS_CODE_BAD_REQUEST);

    const req3 = mockRequest({ params: { short: '' } });
    Validator.on_resolve_request(req3, res, next);
    expect(res.status).toHaveBeenCalledTimes(3);
    expect(res.status).lastCalledWith(STATUS_CODE_BAD_REQUEST);

    expect(next).toHaveBeenCalledTimes(0);
  });
});
