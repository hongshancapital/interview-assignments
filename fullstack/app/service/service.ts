import { query, insert } from '../database/db';
import { NOT_FOUND } from '../common/errMap';

export const getOriginUrl = async (shortUrl: string) => {

  let rows = null;
  try {
    rows = await query({
      short_url: shortUrl
    });

    const data = rows[0] || {};
    const originUrl = data.origin_url;

    if (!originUrl) {
      throw NOT_FOUND;
    }

    return Promise.resolve(originUrl);
  } catch (e) {
    return Promise.reject(e);
  }
}

export const getShortUrl = async (originUrl: string) => {

  // step.1 查询长链是否存在

  // step.2-1 存在 - 返回短链

  // step.2-2 不存在，生成新的短链

  // step.3 hash

  // step.4 62进制转义

  // step.5 唯一索引插入检测

  // step.6-1 插入成功，返回短链

  // step.6-2 插入失败，短链撞hash，回到step3

  return Promise.resolve(originUrl);
}