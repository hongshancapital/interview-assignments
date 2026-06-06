import "reflect-metadata";
import mongoService from '../../../src/services/mongo.service';
import { UrlModel } from '.../../../src/models/url.model';
import { Url } from "../../../src/interfaces/url.interface";

describe('UrlModel', () => {
  beforeAll(async () => {
    await mongoService.connect();
    await UrlModel.deleteMany({});
  });

  afterAll(async () => {
    await UrlModel.deleteMany({});
    await mongoService.disconnect();
  });

  afterEach(async () => {
    await UrlModel.deleteMany({});
  });


  it('should save a new url with unique uid and code', async () => {
    // 创建一条新的url记录
    const url = {
      uid: 'uniqueUid123',
      code: 'unique',
      url: 'http://example.com',
      userUid: 'user123',
    };
    const createdUrl = await UrlModel.create(url);

    expect(createdUrl).toBeDefined();
    expect(createdUrl.uid).toBe(url.uid);
    expect(createdUrl.code).toBe(url.code);
    expect(createdUrl.url).toBe(url.url);
    expect(createdUrl.userUid).toBe(url.userUid);

    const foundUrl = await UrlModel.findOne({ uid: url.uid });
    expect(foundUrl).toBeDefined();
    expect(foundUrl?.uid).toBe(url.uid);
    expect(foundUrl?.code).toBe(url.code);
    expect(foundUrl?.url).toBe(url.url);
    expect(foundUrl?.userUid).toBe(url.userUid);
  });

  it('should not save a url with duplicate uid', async () => {
    // 创建一个包含重复uid的url记录
    const urlWithDuplicateUid = {
      uid: 'uniqueUid123',
      code: 'another',
      url: 'http://example.com/another',
      userUid: 'user456',
    };
    const url: Url = await UrlModel.create(urlWithDuplicateUid);
    expect(url?.uid).toEqual(urlWithDuplicateUid.uid);

    const urlWithDuplicateUid2 = {
      uid: 'uniqueUid123',
      code: '1212',
      url: 'http://example.com/another',
      userUid: 'user456',
    };
    await expect(UrlModel.create(urlWithDuplicateUid2)).rejects.toThrow();
  });

  it('should not save a url with duplicate code', async () => {
    const urlWithDuplicateCode = {
      uid: 'uniqueUid1',
      code: 'same',
      url: 'http://example.com/another',
      userUid: 'user456',
    };
    const url: Url = await UrlModel.create(urlWithDuplicateCode);
    expect(url?.uid).toEqual(urlWithDuplicateCode.uid);
    expect(url?.code).toEqual(urlWithDuplicateCode.code);

    const urlWithDuplicateCod2 = {
      uid: 'uniqueUid2',
      code: 'same',
      url: 'http://example.com/another',
      userUid: 'user456',
    };
    await expect(UrlModel.create(urlWithDuplicateCod2)).rejects.toThrow();
  });
});


