import { expect } from 'chai';

import RestResponse from '../src/infra/RestResponse';

describe('RestResponse', () => {

  describe('constructor', () => {
    it('should data', () => {
      expect(new RestResponse({ data: 'ok' })).to.have.property('data', 'ok');
    });

    it('should status code', () => {
      expect(new RestResponse({ code: 0 })).to.have.property('code', 0);
    });

    it('should message', () => {
      expect(new RestResponse({ message: 'success' })).to.have.property('message', 'success');
    });
  });

  describe('ok', () => {
    it('should data', () => {
      expect(RestResponse.ok('ok')).to.have.property('data', 'ok');
    });

    it('should status code', () => {
      expect(RestResponse.ok('ok')).to.have.property('code', 0);
    });

    it('should message', () => {
      expect(RestResponse.ok('ok')).to.have.property('message', 'success');
    });
  });

  describe('failure', () => {
    it('should failure data', () => {
      expect(RestResponse.failure('failure')).to.have.property('data', null);
    });

    it('should failure status code', () => {
      expect(RestResponse.failure(-1, 'failure')).to.have.property('code', -1);
    });

    it('should failure message', () => {
      expect(RestResponse.failure(-1, 'failure')).to.have.property('message', 'failure');
    });
  });

  describe('setCode', () => {
    it('should set code', () => {
      expect(new RestResponse().setCode(0)).to.have.property('code', 0);
    });
  });

  describe('setMessage', () => {
    it('should set message', () => {
      expect(new RestResponse().setMessage('success')).to.have.property('message', 'success');
    });
  });

  describe('setMessage', () => {
    it('should set data', () => {
      expect(new RestResponse().setData('success')).to.have.property('data', 'success');
    });
  });
});
