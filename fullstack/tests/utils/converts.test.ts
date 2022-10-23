import { converts } from '../../src/utils/converts';
import { expect } from 'chai';

describe('Utils converts boolean', () => {
  it('should return boolean', () => {
    expect(converts.boolean('true')).equal(true);
    expect(converts.boolean('false')).equal(false);
  });

  it('should return undefined', () => {
    expect(converts.boolean('True')).equal(undefined);
    expect(converts.boolean('true ')).equal(undefined);
    expect(converts.boolean('TRUE')).equal(undefined);
    expect(converts.boolean('False')).equal(undefined);
    expect(converts.boolean('false ')).equal(undefined);
    expect(converts.boolean('FALSE')).equal(undefined);
    expect(converts.boolean('')).equal(undefined);
    expect(converts.boolean('213')).equal(undefined);
  });
});

describe('Utils converts number', () => {
  it('should return number', () => {
    expect(converts.number('1')).equal(1);
    expect(converts.number('1.0')).lessThan(2).greaterThan(0);
    expect(converts.number(' 1')).equal(1);
    expect(converts.number('2')).equal(2);
    expect(converts.number('2.1')).lessThan(3).greaterThan(2);
    expect(converts.number('5432')).equal(5432);
  });

  it('should return undefined', () => {
    expect(converts.number('a')).equal(undefined);
    expect(converts.number('b')).equal(undefined);
    expect(converts.number('0x')).equal(undefined);
    expect(converts.number('1.7976931348623157e+309')).equal(undefined);
  });
});

describe('Utils converts string', () => {
  it('should return string', () => {
    expect(converts.string('1')).equal('1');
    expect(converts.string('user')).equal('user');
    expect(converts.string('password')).equal('password');
    expect(converts.string('127.0.0.1')).equal('127.0.0.1');
  });
});
