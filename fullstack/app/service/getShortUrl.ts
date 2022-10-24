import mmh from '../common/mmh';
import { encodeBase62 } from '../common/base62';
import { SUCCESS, NOT_FOUND, DEFAULT_CODE } from '../common/errCode';
import { query, insert } from '../database/db';
import { DataModal } from '..';

export const getShortUrl = async (originUrl: string) : Promise<string | any> => {

  const hash: number = mmh(originUrl);

  try {
    const rows: Array<DataModal> = await query({
      origin_hash: hash 
    });
    const data: DataModal = rows[0] || {};

    if (originUrl !== data.origin_url) {
      // hash conflict
      // TODO
      throw NOT_FOUND;
    }

    if (!data.short_url) {
      throw NOT_FOUND;
    }

    return Promise.resolve(data.short_url);
  } catch (err) {
    if (err !== NOT_FOUND) {
      return Promise.reject(err);
    }
  }

  const shortUrl: string = encodeBase62(hash);

  try {
    const result = await insert({
      short_url: shortUrl,
      origin_hash: hash,
      origin_url: originUrl
    });

    if (result !== SUCCESS) {
      throw DEFAULT_CODE;
    }
  } catch (err) {
    return Promise.reject(err);
  }

  return Promise.resolve(shortUrl);
}