import * as uuid from 'uuid';
import ShortUrl from '../entry/shortUrl.entry';
import shortUrlModel from '../model/shortUrl.model';
import moment from "moment";

const SHORT_DOMAIN = 'http://localhost:3000/short/';

/**
 * 根据短链获取信息
 * @param shortCode
 */
async function getInfoByShortCode(shortCode: string): Promise<ShortUrl> {
  // 可以在redis中缓存，设置一个有效期，有效期可以由短链的获取长链的接口刷新续期
  const [row] = await shortUrlModel.listByShortCode(shortCode);
  return row;
}

/**
 * 先从redis的布隆过滤器中判断是否存在
 - 不存在，写redis、写mysql
 - 存在，继续获取
 * @param appId
 * @param path
 */
async function generate(appId: string, originUrl: string): Promise<string> {
  let shortCode: string;
  let num = 0;
  while (true) {
    const uuidStr: string = uuid.v3(
      `${appId}/${originUrl}/${num}`,
      uuid.v3.URL
    );
    shortCode = uuidStr.split('-')[0];
    // 可以拿布隆过滤器先排查一下
    const row: ShortUrl = await getInfoByShortCode(shortCode);
    if (!row) {
      break;
    }
    // 如果和本次originUrl相同 则返回
    if (row.origin_url === originUrl) {
      return SHORT_DOMAIN + shortCode;
    }
    num = Math.random() * 10;
  }
  // 写入数据库
  await shortUrlModel.save({
    short_code: shortCode,
    app_id: appId,
    origin_url: originUrl,
    accessed_at: new Date()
  });
  // 如果要用到布隆过滤器，则需要将新的短码更新到bloomFilter中
  return SHORT_DOMAIN + shortCode;
}

/**
 * 根据短码获取长链接
 * @param shortCode
 */
async function getOriginUrl(shortCode: string): Promise<string> {
  const row: ShortUrl = await getInfoByShortCode(shortCode);
  if (!row) {
    throw Error('短链不存在');
  }
  // 更新访问时间
  if (!row.accessed_at || moment(row.accessed_at).isBefore(moment(), 'day')) {
    await shortUrlModel.refreshAccessedAt(row.id);
  }
  return row.origin_url;
}

export default {
  generate,
  getOriginUrl
};
