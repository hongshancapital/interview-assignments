import * as utils from './utils';
import {
  SHORT_URL_TOKEN_LENGTH, CHECK_SUM_LENGTH, FALSY_ID, FALSY_RANGE_ID,
} from '../constants';
import { SHORT_URL_PREFIX, RANGE_ID_LEN, ID_MAX } from '../config';

describe('Util Functions Unit Tests', () => {
  it('should tell valid short urls correctly', () => {
    const url1 = `${SHORT_URL_PREFIX}A23=56Z8`;
    const res1 = utils.is_valid_short_url(url1, SHORT_URL_PREFIX);
    expect(res1).toBeTruthy();

    const url2 = '123456';
    const res2 = utils.is_valid_short_url(url2, '');
    expect(res2).toBeFalsy();

    const url3 = '123456789';
    const res3 = utils.is_valid_short_url(url3, '');
    expect(res3).toBeFalsy();

    const url4 = 'abc/PX05=+3L';
    const res4 = utils.is_valid_short_url(url4, 'abc/');
    expect(res4).toBeTruthy();

    const url5 = 'abc/PX05=+3L';
    const res5 = utils.is_valid_short_url(url5, 'abc');
    expect(res5).toBeFalsy();
  });

  it('should success build a valid short url', () => {
    const pattern = `[A-Za-z0-9+=]{${SHORT_URL_TOKEN_LENGTH}}`;
    const res = utils.build_short_url_from(ID_MAX, 'abc/');
    const id = utils.build_short_url_id_from(res, 'abc/');

    expect(res).toMatch(new RegExp(pattern));
    expect(res).toHaveLength(SHORT_URL_TOKEN_LENGTH + 'abc/'.length);
    expect(id).toEqual(ID_MAX);
  });

  it('should build id from valid short_url and return falsy id to invalid input', () => {
    const res1 = utils.build_short_url_id_from('ABCDEFGH', '');
    const res2 = utils.build_short_url_id_from('abc/ABCDEFGH', 'abc/');
    expect(res1).toEqual(res2);

    const res3 = utils.build_short_url_id_from('', '');
    const res4 = utils.build_short_url_id_from('ABC', '');
    const res5 = utils.build_short_url_id_from('ABCDEFGHI', '');
    const res6 = utils.build_short_url_id_from('abc/ABCDEFGH', 'ab');
    expect(res3).toEqual(FALSY_ID);
    expect(res4).toEqual(FALSY_ID);
    expect(res5).toEqual(FALSY_ID);
    expect(res6).toEqual(FALSY_ID);
  });

  it('should generate the same check_sum string of 40 bytes for a given string', () => {
    const pattern = `[0-9abcedf]{${CHECK_SUM_LENGTH}}`;
    const res = utils.calculate_check_sum('www.google.com');
    const result = utils.calculate_check_sum('www.google.com');

    expect(res).toHaveLength(CHECK_SUM_LENGTH);
    expect(res).toMatch(new RegExp(pattern));
    expect(result).toEqual(res);
  });

  it('should make a range id of preferred length', () => {
    const check_sum = utils.calculate_check_sum('www.google.com');
    const range_id = utils.make_range_id_from(check_sum, 5);

    const r = range_id.toString(16);
    const s = check_sum + check_sum;
    expect(s).toContain(r);
  });

  it('should return a falsy range id with wrong input', () => {
    const check_sum = utils.calculate_check_sum('www.google.com');
    const range_id = utils.make_range_id_from(check_sum, 99);
    expect(range_id).toEqual(FALSY_RANGE_ID);
    const c = check_sum.replace(/\d{1}/, 'h');
    const res = utils.make_range_id_from(c, RANGE_ID_LEN);
    expect(res).toEqual(FALSY_RANGE_ID);
  });
});
