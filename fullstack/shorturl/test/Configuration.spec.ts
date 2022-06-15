import { expect } from 'chai';

import path from 'path';

import Configuration from '../src/infra/Configuration';

describe('Configuration', () => {
  Configuration.init({ envFilePath: path.resolve(__dirname, '.env') });

  describe('get', () => {
    it('should load env file', () => {
      expect(Configuration.get("NODE_ENV")).to.be.eql('testing');
    });

    it('should get environment variable', () => {
      expect(Configuration.get("HOME")).to.be.eql(process.env.HOME);
    });

    it('should default value', () => {
      expect(Configuration.get("__DEFAULT_VALUE__", 'abc')).to.be.eql("abc");
    });

    it('should get non-existent environment variable', () => {
      expect(Configuration.get("__DEFAULT_VALUE__")).to.be.an('undefined');
    });
  });

  describe('getOrThrow', () => {
    it('should load env file', () => {
      expect(Configuration.getOrThrow("NODE_ENV")).to.be.eql('testing');
    });

    it('should get environment variable', () => {
      expect(Configuration.getOrThrow("HOME")).to.be.eql(process.env.HOME);
    });

    it('should default value', () => {
      expect(Configuration.getOrThrow("__DEFAULT_VALUE__", 'abc')).to.be.eql("abc");
    });

    it('should get non-existent environment variable', () => {
      expect(() => Configuration.getOrThrow("__DEFAULT_VALUE__")).to.throw(TypeError);
    });
  });
});