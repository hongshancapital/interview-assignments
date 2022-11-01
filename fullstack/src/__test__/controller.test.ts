import {DbAccess} from "../db-access";
import {IdTranslator} from "../id-translator";
import {Cache} from "../cache"
import {Controller} from "../controller";

jest.mock('../db-access');
jest.mock('../id-translator');
jest.mock('../cache');
const DbAccessMock = DbAccess as jest.MockedClass<typeof DbAccess>;
const IdTranslatorMock = IdTranslator as jest.MockedClass<typeof IdTranslator>;
const CacheMock = Cache as jest.MockedClass<typeof Cache>;

describe("controller test", () => {
  const dbAccess = jest.mocked(new DbAccess({} as any));
  const cache = jest.mocked(new Cache({} as any));
  const idTranslator = jest.mocked(new IdTranslator());
  const controller = new Controller(dbAccess, cache, idTranslator);

  beforeEach(() => {
    DbAccessMock.mockClear();
    CacheMock.mockClear();
    IdTranslatorMock.mockClear();
  });

  test('getUrl decode return null', async () => {
    idTranslator.decode.mockReturnValue(null);
    const v = await controller.getUrl('test');
    expect(v).toBeNull();
    expect(idTranslator.decode).toBeCalledWith('test');
  });

  test('getUrl has cache', async () => {
    const e = 'https://baidu.com';
    idTranslator.decode.mockReturnValue(1);
    cache.get.mockResolvedValue(e);

    const v = await controller.getUrl('test');
    expect(v).toBe(e);
    expect(idTranslator.decode).toBeCalledWith('test');
    expect(cache.get).toBeCalledWith('1');
  });

  test('getUrl no cache, no db result', async () => {
    idTranslator.decode.mockReturnValue(1);
    cache.get.mockResolvedValue(null);
    dbAccess.getUrlById.mockResolvedValue(null);

    const v = await controller.getUrl('test');
    expect(v).toBe(null);
    expect(idTranslator.decode).toBeCalledWith('test');
    expect(cache.get).toBeCalledWith('1');
    expect(dbAccess.getUrlById).toBeCalledWith(1);
    expect(cache.set).toBeCalledWith('1', '', 3600);
  });

  test('getUrl no cache, has result', async () => {
    const e = 'https://baidu.com';
    idTranslator.decode.mockReturnValue(1);
    cache.get.mockResolvedValue(null);
    dbAccess.getUrlById.mockResolvedValue(e);

    const v = await controller.getUrl('test');
    expect(v).toBe(e);
    expect(idTranslator.decode).toBeCalledWith('test');
    expect(cache.get).toBeCalledWith('1');
    expect(dbAccess.getUrlById).toBeCalledWith(1);
    expect(cache.set).toBeCalledWith('1', e, 3600);
  });

  test('saveUrl exists', async () => {
    const url = 'https://baidu.com';
    const shortUrl = "abcde";
    dbAccess.getIdByUrl.mockResolvedValue(1);
    idTranslator.encode.mockReturnValue(shortUrl);

    const v = await controller.saveUrl(url);
    expect(v).toBe(shortUrl);
    expect(dbAccess.getIdByUrl).toBeCalledWith(url);
    expect(idTranslator.encode).toBeCalledWith(1);
  })

  test('saveUrl not exists', async () => {
    const url = 'https://baidu.com';
    const shortUrl = "abcde";
    dbAccess.getIdByUrl.mockResolvedValue(null);
    idTranslator.encode.mockReturnValue(shortUrl);
    dbAccess.save.mockResolvedValue(1);

    const v = await controller.saveUrl(url);
    expect(v).toBe(shortUrl);
    expect(dbAccess.getIdByUrl).toBeCalledWith(url);
    expect(idTranslator.encode).toBeCalledWith(1);
    expect(dbAccess.save).toBeCalledWith(url);
  })
});
