/** @format */

import { splitUrl } from '../src/helpers';
// @ts-ignore
import randomString from 'randomstring';

const request = require('supertest');
import { App } from '../src/app';
import { Db } from '../src/db';

describe('test getShortDomainName', () => {
  const app = new App();
  app.service.Model = jest.fn(() => {
    return {
      save: jest.fn().mockImplementationOnce(() => {
        return undefined;
      }),
      urlCode: 12345678,
    };
  });

  Db.connectDB = jest.fn().mockImplementation(() => {
    return undefined;
  });

  app.init();
  const testApi1 =
    '/getShortDomainName?longUrl=https://github.com/scdt-china/interview-assignments/tree/master/fullstack/' +
    'd9f3ce6ec38cd28e61b31a834fe1032abc63d3a1b01687f35430d4c579321ed2/687474703a2f2f6c632d50583276';
  const testApi2 = '/getOriginUrl?urlCode=-FW8nwTm';

  it('first generate shortUrl', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      return undefined;
    });

    request(app.express)
      .get(`${testApi1}${randomString.generate()}`)
      .then((res: any) => {
        const shortUrl = JSON.parse(res.text).shortUrl;
        expect(splitUrl(shortUrl)[0]).toBe('https://github.com/');
        expect(splitUrl(shortUrl)[1].length).toBe(8);
        done();
      });
  });
  it('generate shortUrl', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      return {
        urlCode: 'asdfghjk',
      };
    });
    request(app.express)
      .get(testApi1)
      .then((res: any) => {
        const shortUrl = JSON.parse(res.text).shortUrl;
        expect(splitUrl(shortUrl)[0]).toBe('https://github.com/');
        expect(splitUrl(shortUrl)[1]).toBe('asdfghjk');
        done();
      });
  });
  it('get origin url', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      return {
        longUrl: 'https://github.com/scdt-china/interview-assignments/tree/master/fullstack/',
      };
    });
    request(app.express)
      .get(testApi2)
      .then((res: any) => {
        const originUrl = JSON.parse(res.text).originUrl;
        expect(splitUrl(originUrl)[0]).toBe('https://github.com/');
        expect(splitUrl(originUrl)[1]).toBe('scdt-china/interview-assignments/tree/master/fullstack/');
        done();
      });
  });
  it('generate shortUrl with invalid url', done => {
    const invalidUrl = '/getShortDomainName?longUrl=testTestTest';
    request(app.express)
      .get(invalidUrl)
      .then((res: any) => {
        expect(res.statusCode).toBe(401);
        done();
      });
  });
  it('get origin url with invalid urlCode', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      return undefined;
    });
    const invalidUrl = '/getOriginUrl?urlCode=12345678';
    request(app.express)
      .get(invalidUrl)
      .then(res => {
        expect(res.statusCode).toBe(404);
        done();
      });
  });
  it('generate shortUrl with server error', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      throw new Error('this is an error.');
    });
    request(app.express)
      .get(testApi1)
      .then((res: any) => {
        expect(res.statusCode).toBe(500);
        done();
      });
  });
  it('get origin url with server error', done => {
    app.service.Model.findOne = jest.fn().mockImplementationOnce(() => {
      throw new Error('this is an error.');
    });
    request(app.express)
      .get(testApi2)
      .then((res: any) => {
        expect(res.statusCode).toBe(500);
        done();
      });
  });

  it('get origin url with db connect error', done => {
    Db.connectDB = jest.fn().mockImplementationOnce(() => {
      throw new Error('this is an error.');
    });
    request(app.express)
      .get(testApi2)
      .then((res: any) => {
        expect(res.statusCode).toBe(404);
        done();
      });
  });
});
