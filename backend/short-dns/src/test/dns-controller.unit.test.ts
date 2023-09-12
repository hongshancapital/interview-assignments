/* eslint-disable import/no-extraneous-dependencies */
import sinon from 'sinon';
import { expect } from './expect';
import { genShortDns } from '../controller/dns-service';
import { LongDnsDTO } from '../types/short-dns';
import { RedisUtils } from '../middleware/redis';

describe('dns-controller unit test', async () => {

  let sandbox: sinon.SinonSandbox;
  let longDnsData: LongDnsDTO;
  let baseUrl: string = '/s/';
  let redisUtilsInstance: RedisUtils;
  let createClientStub: sinon.SinonStub;

  before(() => {
    sandbox = sinon.createSandbox();
    redisUtilsInstance = sinon.createStubInstance(RedisUtils);
  });

  beforeEach(() => {
    longDnsData = {
      url: 'fake_url',
      exp: 200,
    };
  });

  afterEach(() => {
    sandbox.restore();
  });

  it('should handle succ with no data to handle', async () =>{
    const setStub = sandbox.stub(redisUtilsInstance, 'set').resolves(true);
    const result = await genShortDns(longDnsData, baseUrl);
    expect(setStub).to.be.calledOnce;
    expect(result).to.be.exist;
  });
});
