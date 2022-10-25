import mmh from '../common/mmh';
import { encodeBase62 } from '../common/base62';
import { addPostfix } from '../common/url';
import { SUCCESS, NOT_FOUND, HASH_ERROR, BASE62_ERROR, DEFAULT_CODE, PARAM_ERROR } from '../common/errCode';
import { query, insert } from '../database/db';
import { DataModal } from '..';

export const getShortUrl = async (originUrl: string) : Promise<string | any> => {

  if (!originUrl) {
    return Promise.reject(PARAM_ERROR);
  }

  let hash: number = NaN;

  try {
    hash = mmh(originUrl);

    if (!hash) {
      throw HASH_ERROR;
    }

    const rows: Array<DataModal> = await query({
      origin_hash: hash 
    });
    const data: DataModal = rows[0] || {};

    if (data.origin_url && originUrl !== data.origin_url) {
      // hash conflict
      // add a postfix at the end of origin url
      const urlWithPostfix: string = addPostfix(originUrl);
      return getShortUrl(urlWithPostfix);
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

  try {
    const shortUrl: string = encodeBase62(hash);

    if (!shortUrl) {
      throw BASE62_ERROR;
    }

    const result = await insert({
      short_url: shortUrl,
      origin_hash: hash,
      origin_url: originUrl
    });

    if (result !== SUCCESS) {
      throw DEFAULT_CODE;
    }

    return Promise.resolve(shortUrl);
  } catch (err) {
    return Promise.reject(err);
  }
}