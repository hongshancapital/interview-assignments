import { Simple, DatabaseError } from '../../../src/service/impl/simple';
import { ShortLinkParams } from '../../../src/service/base';
import { idToStr } from '../../../src/utils';
import { asyncExpect } from '../../__helpers/expects';
import { expect } from 'chai';

const params: ShortLinkParams = {
  host: 'sl-test.cn',
  pg_host: '127.0.0.1',
  pg_port: '5432',
  pg_user: 'sl',
  pg_password: 'password',
};

describe('Simple constructor', () => {
  it('should create simple instance', () => {
    const service = new Simple(params);

    expect(service).exist;
  });
});

describe('Simple _newId', () => {
  it('should return new id', async () => {
    const service = new Simple(params);
    const db = await service.pool.connect();

    await db.query('BEGIN');

    try {
      const id1 = await service._newId(db);

      expect(id1 > 0).equal(true);
      const id2 = await service._newId(db);

      expect(id2 > id1).equal(true);
    } finally {
      await db.query('ROLLBACK');
      await db.release();
    }
  });
});

describe('Simple _createRecord', () => {
  it('should create record', async () => {
    const service = new Simple(params);
    const db = await service.pool.connect();

    await db.query('BEGIN');
    try {
      (
        await asyncExpect(async () => {
          const id = idToStr(
            parseInt((new Date().getTime() * 100 + 100 * Math.random()).toString()),
          );

          await service._createRecord(db, {
            id,
            link: 'http://www.baidu.com',
          });
        })
      ).is.undefined;

      expect(async () => {
        await service._createRecord(db, {
          id: idToStr(parseInt((new Date().getTime() * 1000 + 1000 * Math.random()).toString())),
          link: 'http://www.baidu.com',
        });
      }).to.not.throw('success');

      (
        await asyncExpect(async () => {
          await service._createRecord(db, {
            id: '123456789',
            link: 'http://www.baidu.com',
          });
        })
      ).to.instanceOf(DatabaseError);

      (
        await asyncExpect(async () => {
          await service._createRecord(db, {
            id: '12345678',
            link: 'http://www.baidu.com',
          });
          await service._createRecord(db, {
            id: '12345678',
            link: 'http://www.baidu.com',
          });
        })
      ).to.instanceOf(DatabaseError);

      (
        await asyncExpect(async () => {
          await service._createRecord(db, {
            id: '12345678',
            link: `http://www.baidu.com/${Array(8182).fill('a').join('')}`,
          });
        })
      ).to.instanceOf(DatabaseError);

      (
        await asyncExpect(async () => {
          await service._createRecord(db, {
            id: '',
            link: 'http://www.baidu.com',
          });
        })
      ).to.instanceOf(DatabaseError);
    } finally {
      await db.query('ROLLBACK');
      await db.release();
    }
  });
});

describe('Simple saveLink', () => {
  it('should create record and return short link', async () => {
    const service = new Simple(params);

    (
      await asyncExpect(
        async () =>
          await service.saveLink(`http://www.baidu.com/${Array(8182).fill('a').join('')}`)
      )
    ).to.instanceOf(DatabaseError);

    expect(await service.saveLink('http://www.baidu.com')).length(
      `https://${service.params.host}/`.length + 8,
    );
  });
});

describe('Simple getLink', () => {
  it('should return link in storage', async () => {
    const service = new Simple(params);

    const id = idToStr(parseInt((new Date().getTime() * 100 + 100 * Math.random()).toString()));

    expect(await service.getLink(id)).is.empty;

    const url = await service.saveLink('http://www.baidu.com');

    const { id: id1 } = service.parseShortLink(url);

    expect(id1).is.exist;
    if (id1) {
      console.log(id1);
      expect(await service.getLink(id1)).equals('http://www.baidu.com');
    }

    (await asyncExpect(async () => await service.getLink('http:/123321321/123'))).to.instanceOf(
      Error,
    );
  });
});
