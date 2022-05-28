import { equal, ok } from 'assert';
import number62 from '../../utils/number62';

describe('number62', function () {

  describe('#encode()', function () {
    it('number62.encode(36) should equal "a"', function () {
      equal(number62.encode(36), 'a');
    });
    it('number62.encode(62) should equal "10"', function () {
      equal(number62.encode(62), '10');
    });
    it('number62.encode(Math.pow(62,2)) should equal "100"', function () {
      equal(number62.encode(Math.pow(62, 2)), '100');
    });
    it('number62.encode(Math.pow(62,2),7) should equal "0000100"', function () {
      equal(number62.encode(Math.pow(62, 2), 7), '0000100');
    });
  });

  describe('#decode()', function () {
    it('number62.decode(a) should equal 36', function () {
      equal(number62.decode('a'), 36);
    });
    it('number62.decode(10) should equal 62', function () {
      equal(number62.decode('10'), 62);
    });
    it('number62.decode(100) should equal Math.pow(62,2)', function () {
      equal(number62.decode('100'), Math.pow(62, 2));
    });
    it('number62.decode(0000100) should equal Math.pow(62,2)', function () {
      equal(number62.decode('0000100'), Math.pow(62, 2));
    });
  });

});