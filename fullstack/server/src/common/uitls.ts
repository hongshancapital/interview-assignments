import * as Uuid from 'uuid';
import { mapValues as _mapValues } from 'lodash'

import * as crypto from 'crypto';
import * as md5 from 'blueimp-md5';

// server SERVER_ENV
export function cryptPwd(password, salt = 'shorturl') {
  const md = crypto.createHash('md5');
  return md.update(password + ':' + salt).digest('hex');
}

// web
export function cryptPwdWithoutSalt(password) {
  const md = crypto.createHash('md5');
  return md.update(password).digest('hex');
}

export function md5String(string) {
  return md5(string)
}

// server SERVER_ENV
export function passwordHash(password: string, salt = 'shorturl') {
  return crypto
    .createHmac('sha256', salt)
    .update(password)
    .digest('hex');
}

export const asyncForEach = async (array, callback) => {
  for (let index = 0; index < array.length; index++) {
    await callback(array[index], index, array)
  }
}

/**
 * 指定长度和进制的UUID
 * @param len       长度
 * @param radix     进制
 * @returns {string}
 */
export const uuid = (len, radix = 62) => {
  var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
  var uuid = [], i;
  radix = radix || chars.length;
  if (len) {
    for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
  } else {
    var r;
    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
    uuid[14] = '4';
    for (i = 0; i < 36; i++) {
      if (!uuid[i]) {
        r = 0 | Math.random() * 16;
        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
      }
    }
  }
  return uuid.join('');
}

export const signature = (USER, UKEY, STIME) => {
  return crypto.createHash('sha1').update(USER + UKEY + STIME).digest('hex');//获取签名
}


/**
 * 随机生成字符串
 */
export const generateStr = () => {
  return Uuid.v4().replace(/\-/g, '')
}

/**
 * 生成 Timestamp
 */
export const createTimestamp = () => {
  return parseInt((Date.now() / 1000).toString());
};


/**
 * 延时函数
 * @param {*} ms 毫秒
 */
export async function delay(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}

export const sleep = (timeountMS) => new Promise((resolve) => {
  setTimeout(resolve, timeountMS);
});

export const transformDataSource = (val) => {
  return _mapValues(val, (v) => {
    return typeof v === 'function' ? v.toString() : v
  })
}

export function functionName(function_) {

  if (typeof function_ ==='string') {
    return function_
  }
  
	if (typeof function_ !== 'function') {
		throw new TypeError('Expected a function');
	}

	return function_.displayName || function_.name || (/function ([^(]+)?\(/.exec(function_.toString()) || [])[1];
}
