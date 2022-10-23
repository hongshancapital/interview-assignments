import http from 'http';
import { Router } from '../src/router';
import { expect } from 'chai';
import { stub, spy } from 'sinon';
import { ResBody } from '../src/utils';
import { IShortLinkService } from '../src/service/base';

function defineProperty(obj: any, properties: any) {
  Object.keys(properties).forEach(key => {
    Object.defineProperty(obj, key, {
      configurable: true,
      get() {
        return properties[key];
      },
    });
  });
  return obj;
}

function getService(): IShortLinkService {
  const service: IShortLinkService = {
    saveLink: async (link: string) => {
      console.log(link);
      return 'http://sl.cn/12345678';
    },
    getLink: async (id: string) => {
      console.log(id);
      return 'http://www.baidu.com';
    },
    parseShortLink: (link: string) => {
      console.log(link);
      return { id: '12345678' };
    },
  };

  return service;
}

describe('Router saveLink', () => {
  const router = new Router(getService());
  const res = Object.assign(Object.create(http.OutgoingMessage.prototype), {
    send: (body: ResBody<string>) => {
      console.log(body);
    },
  });
  const _stubSaveLink = stub(router.service, 'saveLink');
  const _stubRes = stub(res, 'send');

  afterEach(() => {
    _stubSaveLink.reset();
    _stubRes.reset();
  });

  it('should return saveLink invalid', async () => {
    const req = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: {},
      body: {},
    });
    const _spyReqQuery = spy(req, 'query', ['get']);
    const _spyReqBody = spy(req, 'body', ['get']);

    await router.saveLink(req, res);

    expect(_spyReqQuery.get.called).equal(false);
    expect(_spyReqBody.get.called).equal(true);
    expect(_stubSaveLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_link_invalid',
      }),
    ).equal(true);

    _stubSaveLink.reset();
    _stubRes.reset();

    const req1 = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: {},
      body: { link: 'http:/www.baidu' },
    });
    const _spyReq1Query = spy(req1, 'query', ['get']);
    const _spyReq1Body = spy(req1, 'body', ['get']);

    await router.saveLink(req1, res);

    expect(_spyReq1Query.get.called).equal(false);
    expect(_spyReq1Body.get.called).equal(true);
    expect(_stubSaveLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_link_invalid',
      }),
    ).equal(true);
  });

  it('should return saveLink valid', async () => {
    const req = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: {},
      body: { link: 'http://www.baidu.com' },
    });
    const _spyReqQuery = spy(req, 'query', ['get']);
    const _spyReqBody = spy(req, 'body', ['get']);

    await router.saveLink(req, res);

    expect(_spyReqQuery.get.called).equal(false);
    expect(_spyReqBody.get.called).equal(true);
    expect(_stubSaveLink.called).equal(true);
    expect(_stubSaveLink.calledWithMatch('http://www.baidu.com')).equal(true);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: true,
      }),
    ).equal(true);
  });
});

describe('Router getLink', () => {
  const router = new Router(getService());
  const res = Object.assign(Object.create(http.OutgoingMessage.prototype), {
    send: (body: ResBody<string>) => {
      console.log(body);
    },
  });
  const _stubGetLink = stub(router.service, 'getLink');
  const _stubParseShortLink = stub(router.service, 'parseShortLink');
  const _stubRes = stub(res, 'send');

  afterEach(() => {
    _stubGetLink.reset();
    _stubParseShortLink.reset();
    _stubRes.reset();
  });

  it('should return getLink invalid', async () => {
    const req = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: {},
      body: {},
    });
    const _spyReqQuery = spy(req, 'query', ['get']);
    const _spyReqBody = spy(req, 'body', ['get']);

    await router.getLink(req, res);

    expect(_spyReqQuery.get.called).equal(true);
    expect(_spyReqBody.get.called).equal(false);
    expect(_stubGetLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_invalid',
      }),
    ).equal(true);

    _stubGetLink.reset();
    _stubRes.reset();

    const req1 = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: { id: '1234567' },
      body: {},
    });
    const _spyReq1Query = spy(req1, 'query', ['get']);
    const _spyReq1Body = spy(req1, 'body', ['get']);

    await router.getLink(req1, res);

    expect(_spyReq1Query.get.called).equal(true);
    expect(_spyReq1Body.get.called).equal(false);
    expect(_stubGetLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_id_invalid',
      }),
    ).equal(true);

    _stubGetLink.reset();
    _stubRes.reset();

    const req2 = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: { link: 'http://sl.aaa.cn/1234567' },
      body: {},
    });

    _stubParseShortLink.returns({ error: 'host invalid' });
    const _spyReq2Query = spy(req2, 'query', ['get']);
    const _spyReq2Body = spy(req2, 'body', ['get']);

    await router.getLink(req2, res);

    expect(_spyReq2Query.get.called).equal(true);
    expect(_spyReq2Body.get.called).equal(false);
    expect(_stubGetLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_link_invalid',
      }),
    ).equal(true);

    _stubGetLink.reset();
    _stubRes.reset();

    const req3 = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: { link: 'http://sl.cn/123456789' },
      body: {},
    });

    _stubParseShortLink.returns({ id: '123456789' });
    const _spyReq3Query = spy(req3, 'query', ['get']);
    const _spyReq3Body = spy(req3, 'body', ['get']);

    await router.getLink(req3, res);

    expect(_spyReq3Query.get.called).equal(true);
    expect(_spyReq3Body.get.called).equal(false);
    expect(_stubGetLink.called).equal(false);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: false,
        errCode: 'param_id_invalid',
      }),
    ).equal(true);
  });

  it('should return getLink valid', async () => {
    const req = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: { id: '12345678' },
      body: {},
    });
    const _spyReqQuery = spy(req, 'query', ['get']);
    const _spyReqBody = spy(req, 'body', ['get']);

    await router.getLink(req, res);

    expect(_spyReqQuery.get.called).equal(true);
    expect(_spyReqBody.get.called).equal(false);
    expect(_stubGetLink.called).equal(true);
    expect(_stubGetLink.calledWith('12345678')).equal(true);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: true,
      }),
    ).equal(true);

    _stubGetLink.reset();
    _stubRes.reset();

    const req1 = defineProperty(Object.create(http.IncomingMessage.prototype), {
      query: { link: 'http://sl.cn/12345678' },
      body: {},
    });

    _stubParseShortLink.returns({ id: '12345678' });
    const _spyReq1Query = spy(req1, 'query', ['get']);
    const _spyReq1Body = spy(req1, 'body', ['get']);

    await router.getLink(req1, res);

    expect(_spyReq1Query.get.called).equal(true);
    expect(_spyReq1Body.get.called).equal(false);
    expect(_stubGetLink.called).equal(true);
    expect(_stubGetLink.calledWith('12345678')).equal(true);
    expect(_stubRes.calledOnce).equal(true);
    expect(
      _stubRes.calledWithMatch({
        success: true,
      }),
    ).equal(true);
  });
});
