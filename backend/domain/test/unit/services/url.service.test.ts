import "reflect-metadata";

import mongoService from '../../../src/services/mongo.service';
import urlService from '../../../src/services/url.service';

import { Url } from '../../../src/interfaces/url.interface';
import { UrlModel } from "../../../src/models/url.model";

describe('Service: urlService', () => {
  const url = 'www.baidu.com/unit'; 
  let urlInstance: Url;
  const user = {
    uid: '1212121',
  };

  beforeAll(async ()=> {
    await mongoService.connect();
  });

  afterAll(async () => {
    await UrlModel.deleteMany({});
    await mongoService.disconnect();
  });
    
  it('urlService createByOption', async () => {
    urlInstance = await urlService.createByOption(url, user.uid);

    expect(urlInstance.url).toEqual(url);
    expect(urlInstance.userUid).toEqual(user.uid);
  });

  it('urlService findByCode', async () => {
    const result = await urlService.findByCode(urlInstance.code);

    expect(result).toBeDefined();
    if (result) {
      expect(result.uid).toEqual(urlInstance.uid);
    }
        
  });

  it('urlService findByOption', async () => {
    const result = await urlService.findByOption(urlInstance.url, user.uid);

    expect(result).toBeDefined();
    if (result) {
      expect(result.uid).toEqual(urlInstance.uid);
      expect(result.userUid).toEqual(user.uid);
    }
        
  });

});