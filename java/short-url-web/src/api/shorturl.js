import {fetch} from '../utils/fetch';
const SURL = '/surl';

/**
 * 生成短链接
 */
export const shorten = (body, callback) => fetch(SURL + '/shorten', {callback,reqBody: body }, 'POST');

/**
 * 查询原始url
 */
export const getLongUrl = (paths, callback) => fetch(SURL + '/{key}'.replace('{' + 'key' + '}', encodeURIComponent(String(paths.key))), {callback}, 'GET');


