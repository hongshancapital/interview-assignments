import { Elasticsearch } from '../../src/service/impl/elasticsearch';
import { expect } from 'chai';

describe('Factory getService elasticsearch', () => {
  let getService: () => any;

  before(() => {
    const { deleteCache } = require('../__helpers/elasticsearch.env');

    deleteCache();
    ({ getService } = require('../../src/service/factory'));
  });

  it('should return system short link service instance', () => {
    const result: any = getService();

    expect(result instanceof Elasticsearch).equal(true);
    expect(result.params?.host).equal('sl.test.cn');
    expect(result.params?.es_host).equal('192.168.1.10');
  });
});
