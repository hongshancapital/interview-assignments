import { query } from '../database/db';
import { PARAM_ERROR, NOT_FOUND } from '../common/errCode';

export const getOriginUrl = async (shortUrl: string): Promise<string | any> => {
  let rows = null;

  if (!shortUrl) {
    return Promise.reject(PARAM_ERROR);
  }

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
  } catch (err) {
    return Promise.reject(err);
  }
}