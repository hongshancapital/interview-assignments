import { mockRequest, mockResponse } from 'jest-mock-req-res';
import { STATUS_CODE_OK, STATUS_CODE_CREATED, STATUS_CODE_NOT_FOUND } from '../constants';
import { SHORT_URL_PREFIX, ID_MAX, ID_MIN } from '../config';

import container from '../server/inversify.config';
import Controller from './controller';
import Interfaces from '../interfaces';

import { DB_Provider_Intf } from '../id_service/db_provider_intf';

const controller = container.get<Controller>(Controller);
afterAll(async () => { await container.get<DB_Provider_Intf>(Interfaces.DB_Provider).stop(); });

beforeEach(() => { jest.clearAllMocks(); });

describe('Controller::create_short_url() Test', () => {
  const test_url = `https://www.na.io/s?${Math.floor(Math.random() * (ID_MAX - ID_MIN) + ID_MIN)}`;

  it('should proceed and return \'created\' when data is valid and services ready', async () => {
    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    expect(response.status).toBeCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_CREATED);
  });

  it('should return result without creating if a short url exists for the given url', async () => {
    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    await controller.create_short_url(request, response);
    expect(response.status).toBeCalledTimes(2);
    expect(response.status).toHaveBeenLastCalledWith(STATUS_CODE_OK);
  });
});

describe('Controller::resolve_short_url  Test', () => {
  const test_url = 'https://www.notavailable.io:8080/request=search?name=nobody&age=99';

  it('should return OK and result if the short url mapping is found',
    async () => {
      const request = mockRequest({ body: { original_url: test_url } });
      const response = mockResponse({
        json: jest.fn((x) => {
          response.locals.val = x; return this;
        }),
      });

      await controller.create_short_url(request, response);
      const { short_url } = response.locals.val;
      expect(short_url).not.toBeUndefined();

      const req = mockRequest({ params: { short_url } });
      const res = mockResponse({ json: jest.fn((x) => { res.locals.val = x; return this; }) });

      await controller.resolve_short_url(req, res);
      const { original_url } = res.locals.val;
      expect(original_url).not.toBeUndefined();
      expect(original_url).toEqual(test_url);
    });

  it('should return NOT FOUND when requested to resolve short url not exist',
    async () => {
      const request = mockRequest({ params: { short_url: `${SHORT_URL_PREFIX}ABCDEFGH` } });
      const response = mockResponse();
      await controller.resolve_short_url(request, response);

      expect(response.status).toBeCalledTimes(1);
      expect(response.status).toHaveBeenCalledWith(STATUS_CODE_NOT_FOUND);
    });
});
