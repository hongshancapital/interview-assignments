import { Request, Response } from 'express';
import Hashids from 'hashids';
import config from 'config';
import { Url, MAX_URL_LENGTH } from "../entity/Url.js";
import { success, failure } from "../util.js";

const hashids = new Hashids(config.get('salt'));
const encoder = {
  /**
   * 短域名读取接口：接受短域名信息，返回长域名信息。
   */
  getUrl(req: Request, res: Response) {
    const { short } = req.params;
    if (!short || short.length > 8) {
      return failure('短域名长度大于8或为空，请检查');
    }
    const decodeResult = hashids.decode(req.params.short);
    if (!decodeResult.length) {
      return success('', '不存在对应url');
    }
    return req.redisClient.get(short).then((rs: string) => {
      if (!rs) {
        return req.db.getRepository(Url).find({
          select: ['original'],
          where: {
            id: decodeResult[0]
          }
        }).then((data) => {
          const result = data[0] ? data[0].original : '';
          if (result) {
            req.redisClient.set(short, result);
          }
          return success(result);
        });
      }
      return success(rs);
    });
  },
  /**
   * 短域名存储接口：接受长域名信息，返回短域名信息
   */
  setUrl(req: Request, res: Response) {
    let { url } = req.body;
    if (!url || url.length > MAX_URL_LENGTH) {
      return failure(`域名为空或长度超过${MAX_URL_LENGTH}，请检查`);
    }
    url = url.trim();
    function findIfExists (url: string) {
      return req.db.getRepository(Url).find({
        select: ['id'],
        where: {
          original: url
        }
      }).then((data) => {
        return data[0];
      });
    }

    return findIfExists(url).then((record) => {
      if (record) {
        return record;
      }
      return req.db.save(
        req.db.create(Url, {
          original: url
        })
      ).catch(() => {
        return findIfExists(url)
      });
    }).then((record) => {
      // 加密id，限制结果最短长度为6
      if (record) {
        return success(hashids.encode(record.id, 6));
      }
      return failure('生成短域名失败');
    });
  }
};
export default encoder;
