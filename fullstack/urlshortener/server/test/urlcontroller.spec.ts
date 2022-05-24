import { expect } from 'chai';
import * as sinon from 'sinon';
import { DeepPartial, Repository } from 'typeorm';
import * as uuid from 'uuid';
import * as express from 'express';
import * as db from '../src/component/db';
import * as randomcode from '../src/component/randomcode';
import * as constants from '../src/component/constant';
import { Url } from '../src/entity/url';
import { UrlController } from '../src/controller/urlcontroller';

const sandbox = sinon.createSandbox();

function mockRepository<T>(entityClass: new () => T): sinon.SinonStubbedInstance<Repository<T>> {
  const repository = sandbox.createStubInstance(Repository);
  sandbox.stub(db, 'getRepository').withArgs(entityClass).returns(repository);
  return repository;
}

describe('url controller test', () => {

  afterEach(() => {
    sandbox.restore();
  });

  it('should generate a short url by original url', async () => {
    const repository = mockRepository(Url);
    repository.create.callsFake((url: DeepPartial<Url>) => url as Url);
    repository.save.callsFake((url: DeepPartial<Url>) => Promise.resolve({ ...url, id: uuid.v4() } as Url));
    sandbox.stub(randomcode, 'generate').returns('dummycode');
    sandbox.stub(constants, 'BASE_URL').value('http://short.domain');
    const controller = new UrlController();
    const response = await controller.create({
      originalUrl: 'http://longlong.domain/longlongpath',
    });
    expect(response).to.deep.eq({
      shortUrl: 'http://short.domain/dummycode',
      originalUrl: 'http://longlong.domain/longlongpath'
    });
  });

  it('should generate another shorturl if shorturl is in use', async () => {
    const repository = mockRepository(Url);
    repository.create.callsFake((url: DeepPartial<Url>) => url as Url);
    repository.save.onCall(0).throws(new Error('duplicate key'));
    repository.save.onCall(1).callsFake((url: DeepPartial<Url>) => Promise.resolve({ ...url, id: uuid.v4() } as Url));

    const randomcodeStub = sandbox.stub(randomcode, 'generate');
    randomcodeStub.onCall(0).returns('dummycode');
    randomcodeStub.onCall(1).returns('dummycode2');

    sandbox.stub(constants, 'BASE_URL').value('http://short.domain');

    const controller = new UrlController();
    const response = await controller.create({
      originalUrl: 'http://longlong.domain/longlongpath',
    });
    expect(response).to.deep.eq({
      shortUrl: 'http://short.domain/dummycode2',
      originalUrl: 'http://longlong.domain/longlongpath'
    });
  });

  it('should redirect to the longurl with shorturl', async () => {
    const shortUrlCode = 'dummycode';
    const longUrl = 'http://longlongurl';
    const repository = mockRepository(Url);
    repository.findOne.withArgs({ code: shortUrlCode }).resolves({
      id: uuid.v4(),
      code: shortUrlCode,
      originalUrl: longUrl,
      createTime: new Date(),
    });
    const controller = new UrlController();
    const res = {
      redirect: sinon.stub(),
    };
    await controller.redirect(shortUrlCode, res as unknown as express.Response);
    expect(res.redirect.withArgs(longUrl).calledOnce).to.be.true;
  });
});
