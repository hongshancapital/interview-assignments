import { PaddingOption, idToStr, strToId } from '../../src/utils/number';
import { expect } from 'chai';

describe('Utils number idToStr', () => {
  it('should return string use 62 system', () => {
    expect(idToStr(1)).equal('1');
    expect(idToStr(10)).equal('a');
    expect(idToStr(61)).equal('Z');
    expect(idToStr(62)).equal('10');
    expect(idToStr(9223372036854770)).equal('Gf4DuF32O');
    expect(idToStr(BigInt('9223372036854771'))).equal('Gf4DuF32P');
  });

  it('should return padded string', () => {
    const padLeft: PaddingOption = { pos: 'left', len: 8, char: '0' };
    const padRight: PaddingOption = { pos: 'right', len: 4, char: '-' };

    expect(idToStr(1, padLeft)).equal('00000001');
    expect(idToStr(10, padLeft)).equal('0000000a');
    expect(idToStr(61, padLeft)).equal('0000000Z');
    expect(idToStr(62, padLeft)).equal('00000010');
    expect(idToStr(9223372036854770, padLeft)).equal('Gf4DuF32O');
    expect(idToStr(BigInt('9223372036854771'), padLeft)).equal('Gf4DuF32P');

    expect(idToStr(1, padRight)).equal('1---');
    expect(idToStr(10, padRight)).equal('a---');
    expect(idToStr(61, padRight)).equal('Z---');
    expect(idToStr(62, padRight)).equal('10--');
    expect(idToStr(9223372036854770, padRight)).equal('Gf4DuF32O');
    expect(idToStr(BigInt('9223372036854771'), padRight)).equal('Gf4DuF32P');
  });
});

describe('Utils number strToId', () => {
  it('should return number', () => {
    expect(strToId('1')).equal(BigInt(1));
    expect(strToId('a')).equal(BigInt(10));
    expect(strToId('Z')).equal(BigInt(61));
    expect(strToId('10')).equal(BigInt(62));

    expect(strToId('0001')).equal(BigInt(1));
    expect(strToId('000a')).equal(BigInt(10));
    expect(strToId('0000000Z')).equal(BigInt(61));
    expect(strToId('00000010')).equal(BigInt(62));

    expect(strToId('Gf4DuF32O')).equal(BigInt(9223372036854770));
    expect(strToId('Gf4DuF32P')).equal(BigInt('9223372036854771'));
  });

  it('should throw not support', () => {
    expect(() => strToId('11111111111111111111')).throw(Error, 'not support string');
    expect(() => strToId('--------')).throw(Error, 'not support string');
    expect(() => strToId('--1--')).throw(Error, 'not support string');
  });
});
