import { Simple } from '../../src/service/impl/simple';
import { expect } from 'chai';

describe('Factory getService simple', () => {
  let getService: () => any;

  before(() => {
    const { deleteCache } = require('../__helpers/simple.env');

    deleteCache();
    ({ getService } = require('../../src/service/factory'));
  });

  it('should return system short link service instance', () => {
    const result: any = getService();

    expect(result instanceof Simple).equal(true);
    expect(result.params?.host).equal('sl.test.cn');
    expect(result.params?.pg_host).equal('192.168.1.10');
  });
});
