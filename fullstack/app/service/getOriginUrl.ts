import { query } from '../database/db';
import { PARAM_ERROR, NOT_FOUND } from '../common/errCode';
import { removePostfix } from '../common/url';
import { DataModal } from '..';

export const getOriginUrl = async (shortUrl: string): Promise<string | any> => {
  if (!shortUrl) {
    return Promise.reject(PARAM_ERROR);
  }

  let rows: Array<DataModal> = [];

  try {
    rows = await query({
      short_url: shortUrl
    });

    const data: DataModal = rows[0] || {};

    if (!data.origin_url) {
      throw NOT_FOUND;
    }

    const originUrl: string = removePostfix(data.origin_url);

    return Promise.resolve(originUrl);
  } catch (err) {
    return Promise.reject(err);
  }
}