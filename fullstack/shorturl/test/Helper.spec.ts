import { expect } from 'chai';

import { Helper } from "../src/infra/Helper";

describe('Helper', () => {
  describe('head', () => {
    it('should normal array', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.head(array)).to.be.eql(Helper.nth(array, 0));
    });

    it('should empty array', () => {
      const array = [];
      expect(Helper.head(array)).to.be.eql(undefined);
    });
  });

  describe('last', () => {
    it('should normal array', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.last(array)).to.be.eql(Helper.nth(array, -1));
    });

    it('should empty array', () => {
      const array = [];
      expect(Helper.last(array)).to.be.eql(undefined);
    });
  });

  describe('nth', () => {
    it('should normal array and positive index', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.nth(array, 0)).to.be.eql(Helper.head(array));
    });

    it('should normal array and negative index', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.nth(array, -1)).to.be.eql(Helper.last(array));
    });

    it('should empty array', () => {
      const array = [];
      expect(Helper.nth(array, 0)).to.be.eql(undefined);
    });
  });

  describe('at', () => {
    it('should normal array and positive index', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.nth(array, 0)).to.be.eql(Helper.at(array, 0));
    });

    it('should normal array and negative index', () => {
      const array = [1, 2, 3, 4, 5];
      expect(Helper.nth(array, -1)).to.be.eql(Helper.at(array, -1));
    });

    it('should empty array', () => {
      const array = [];
      expect(Helper.nth(array, 0)).to.be.eql(Helper.at(array, 0));
    });
  });
});