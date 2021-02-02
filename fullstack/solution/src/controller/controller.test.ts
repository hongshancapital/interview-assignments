import { mockRequest, mockResponse } from 'jest-mock-req-res';
import Controller from './controller';
import {
  FALSY_ID,
  STATUS_CODE_OK,
  STATUS_CODE_CREATED,
  STATUS_CODE_NOT_FOUND,
  STATUS_CODE_INTERNAL_SERVER_ERROR,
} from '../constants';
import { SHORT_URL_PREFIX } from '../config';

import App_Cache from '../cache/app_cache';
import Data_Storage from '../data_storage/data_storage';
import DB_Provider_MySQL from '../id_service/db_provider_mysql';
import ID_Service_Repository_Impl from '../id_service/id_service_repository_impl';
import ID_Service_Impl from '../id_service/id_service_impl';

import { ID_Service_Response, ID_Service_Result_Code } from '../id_service/id_service_intf';

jest.mock('../data_storage/data_storage');
jest.mock('../id_service/id_service_repository_impl');
jest.mock('../id_service/db_provider_mysql');
jest.mock('../id_service/id_service_impl');

describe('Controller::create_short_url() Test', () => {
  const db_provider_dummy = new DB_Provider_MySQL();
  const id_service_repo_dummy = new ID_Service_Repository_Impl(db_provider_dummy);
  const test_url = 'https://www.notavailable.io:8080/request=search?name=nobody&age=99';
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should proceed and return \'created\' when data is valid and services ready', async () => {
    const cache = new App_Cache();
    const id_service = new ID_Service_Impl(id_service_repo_dummy);
    const storage = new Data_Storage();
    const controller = new Controller(cache, storage, id_service);

    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    expect(response.status).toBeCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_CREATED);
  });

  it('should return interal server error when input is valid but ID service down', async () => {
    const cache = new App_Cache();
    const id_service = new ID_Service_Impl(id_service_repo_dummy);
    const storage = new Data_Storage();
    const controller = new Controller(cache, storage, id_service);
    const spy = jest.spyOn(id_service, 'request').mockResolvedValueOnce(
      new ID_Service_Response(ID_Service_Result_Code.EXCEPTION, FALSY_ID, 'EXCEPTION'),
    );
    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    expect(spy).toHaveBeenCalledTimes(1);
    expect(response.status).toBeCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_INTERNAL_SERVER_ERROR);
  });

  it('should return internal server error when data is valid but storage fails', async () => {
    const cache = new App_Cache();
    const id_service = new ID_Service_Impl(id_service_repo_dummy);
    const storage = new Data_Storage();
    const controller = new Controller(cache, storage, id_service);
    const spy = jest.spyOn(storage, 'save_a_mapping').mockResolvedValueOnce(false);
    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    expect(spy).toHaveBeenCalledTimes(1);
    expect(response.status).toBeCalledTimes(1);
    expect(response.status).toHaveBeenCalledWith(STATUS_CODE_INTERNAL_SERVER_ERROR);
  });

  it('should return result without creating if a short url already be there', async () => {
    const cache = new App_Cache();
    const id_service = new ID_Service_Impl(id_service_repo_dummy);
    const storage = new Data_Storage();
    const controller = new Controller(cache, storage, id_service);

    const request = mockRequest({ body: { original_url: test_url } });
    const response = mockResponse();

    await controller.create_short_url(request, response);
    await controller.create_short_url(request, response);
    expect(response.status).toBeCalledTimes(2);
    expect(response.status).toHaveBeenLastCalledWith(STATUS_CODE_OK);
  });
});

describe('Controller::resolve_short_url  Test', () => {
  const db_provider_dummy = new DB_Provider_MySQL();
  const id_service_repo_dummy = new ID_Service_Repository_Impl(db_provider_dummy);
  const test_url = 'https://www.notavailable.io:8080/request=search?name=nobody&age=99';
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should return OK and result if the short url mapping is found',
    async () => {
      const cache = new App_Cache();
      const id_service = new ID_Service_Impl(id_service_repo_dummy);
      const storage = new Data_Storage();
      const controller = new Controller(cache, storage, id_service);
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
      const cache = new App_Cache();
      const id_service = new ID_Service_Impl(id_service_repo_dummy);
      const storage = new Data_Storage();
      const controller = new Controller(cache, storage, id_service);

      const request = mockRequest({ params: { short_url: `${SHORT_URL_PREFIX}ABCDEFGH` } });
      const response = mockResponse();
      await controller.resolve_short_url(request, response);

      expect(response.status).toBeCalledTimes(1);
      expect(response.status).toHaveBeenCalledWith(STATUS_CODE_NOT_FOUND);
    });
});
