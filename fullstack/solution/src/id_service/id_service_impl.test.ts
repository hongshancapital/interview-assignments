import { ID_Service_Response, ID_Service_Result_Code } from './id_service_intf';
import ID_Service_Impl from './id_service_impl';
import { FALSY_ID } from '../constants';

describe('ID_Service_Impl Unit Tests', () => {
  it('should succeed with a valid preferred range', async () => {
    const repository_mock = {
      async create_one_with_range(): Promise<number> { return Promise.resolve(259); },
      async create_one_roughly(): Promise<number> { return Promise.resolve(FALSY_ID); },
    };

    const spy1 = jest.spyOn(repository_mock, 'create_one_with_range');
    const spy2 = jest.spyOn(repository_mock, 'create_one_roughly');

    const id_service = new ID_Service_Impl(repository_mock);
    const res:ID_Service_Response = await id_service.request(0x1);

    expect(spy1).toBeCalledTimes(1);
    expect(spy2).not.toBeCalled();
    expect(res.code()).toEqual(ID_Service_Result_Code.SUCCESS);
    expect(res.value()).toEqual(259);
  });

  it('should succeed without preferred range', async () => {
    const repository_mock = {
      async create_one_with_range(): Promise<number> { throw new Error('Should Not Be Called'); },
      async create_one_roughly(): Promise<number> { return Promise.resolve(13579); },
    };

    const spy1 = jest.spyOn(repository_mock, 'create_one_with_range');
    const spy2 = jest.spyOn(repository_mock, 'create_one_roughly');

    const id_service = new ID_Service_Impl(repository_mock);
    const res:ID_Service_Response = await id_service.request();

    expect(spy1).not.toBeCalled();
    expect(spy2).toBeCalledTimes(1);
    expect(res.code()).toEqual(ID_Service_Result_Code.SUCCESS);
    expect(res.value()).toEqual(13579);
  });

  it('should be notified if repo fails in creation', async () => {
    const repository_mock = {
      async create_one_with_range(): Promise<number> { return Promise.resolve(FALSY_ID); },
      async create_one_roughly():Promise<number> { return Promise.resolve(FALSY_ID); },
    };
    const spy1 = jest.spyOn(repository_mock, 'create_one_with_range');
    const spy2 = jest.spyOn(repository_mock, 'create_one_roughly');
    const id_service = new ID_Service_Impl(repository_mock);

    const res:ID_Service_Response = await id_service.request(0x1);

    expect(spy1).toBeCalledTimes(1);
    expect(spy2).toBeCalledTimes(1);
    expect(res.code()).toEqual(ID_Service_Result_Code.FAILURE);
  });

  it('should be notified if exception happend in repo ', async () => {
    const repository_mock = {
      async create_one_with_range(): Promise<number> { throw new Error('Exception: create_one_with_range()'); },
      async create_one_roughly():Promise<number> { return Promise.resolve(FALSY_ID); },
    };
    const spy1 = jest.spyOn(repository_mock, 'create_one_with_range');
    const spy2 = jest.spyOn(repository_mock, 'create_one_roughly');
    const id_service = new ID_Service_Impl(repository_mock);

    const res:ID_Service_Response = await id_service.request(0x1);

    expect(spy1).toBeCalledTimes(1);
    expect(spy2).not.toBeCalled();
    expect(res.code()).toEqual(ID_Service_Result_Code.EXCEPTION);
  });
});
