import { isEmpty, isLink, isID } from '../../src/utils/validate';
import { expect } from 'chai';

describe('Utils validate isEmpty', () => {
  it('should return is empty', () => {
    expect(isEmpty('')).equal(true);
    expect(isEmpty(undefined)).equal(true);
    expect(isEmpty(null)).equal(true);
    expect(isEmpty()).equal(true);

    expect(isEmpty(1)).equal(false);
    expect(isEmpty('122')).equal(false);
    expect(isEmpty(0)).equal(false);
    expect(isEmpty('0')).equal(false);
  });
});

describe('Utils validate isLink', () => {
  it('should return is link', () => {
    expect(isLink('')).equal(false);
    expect(isLink(undefined)).equal(false);
    expect(isLink('ftp://a')).equal(false);
    expect(
      isLink(
        `http://a${Array(8182 - 7)
          .fill('a')
          .join('')}`,
      ),
    ).equal(false);
    expect(isLink('https://a/?id=1\n')).equal(false);

    expect(isLink('http://a')).equal(true);
    expect(isLink('https://a/?id=1')).equal(true);
    expect(
      isLink(
        `http://a${Array(8182 - 8)
          .fill('a')
          .join('')}`,
      ),
    ).equal(true);
  });
});

describe('Utils validate isID', () => {
  it('should return is id', () => {
    expect(isID('')).equal(false);
    expect(isID('123456')).equal(false);
    expect(isID('asdvfdf')).equal(false);
    expect(isID('asdvfdf331')).equal(false);
    expect(isID('-----')).equal(false);
    expect(isID('ab45678-$')).equal(false);

    expect(isID('--------')).equal(true);
    expect(isID('12345678')).equal(true);
    expect(isID('ab45678-')).equal(true);
    expect(isID('abcdeffg')).equal(true);
  });
});
