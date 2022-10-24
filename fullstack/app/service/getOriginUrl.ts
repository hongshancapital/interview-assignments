import { query } from '../database/db';
import { PARAM_ERROR, NOT_FOUND } from '../common/errCode';
import { DataModal } from '..';

export const getOriginUrl = async (shortUrl: string): Promise<string | any> => {
  let rows: Array<DataModal> = [];

  if (!shortUrl) {
    return Promise.reject(PARAM_ERROR);
  }

  try {
    rows = await query({
      short_url: shortUrl
    });

    const data: DataModal = rows[0] || {};

    if (!data.origin_url) {
      throw NOT_FOUND;
    }

    return Promise.resolve(data.origin_url);
  } catch (err) {
    return Promise.reject(err);
  }
}