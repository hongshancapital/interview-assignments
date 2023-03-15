/** @format */

import { Response, Request } from 'express';
import validUrl from 'valid-url';
import { splitUrl } from './helpers';
import { nanoid } from 'nanoid';
import Url from './model/url';

/**
 * 请求处理服务
 */
export class Service {
  public Model;

  constructor() {
    const url = new Url();
    this.Model = url.getModel();
  }

  /**
   * 获取短链接
   * @param {Request} req 请求
   * @param {Response} res 响应
   */
  public async getShortDomainName(req: Request, res: Response) {
    const longUrl = req.query.longUrl;
    try {
      if (typeof longUrl === 'string' && validUrl.isUri(longUrl)) {
        let url = await this.Model.findOne({ longUrl });
        if (url) {
          res.json({
            shortUrl: `${splitUrl(longUrl)[0]}${url.urlCode}`,
          });
        } else {
          const urlCode = nanoid(8);
          url = new this.Model({
            longUrl,
            urlCode,
          });
          await url.save();
          res.json({
            shortUrl: `${splitUrl(longUrl)[0]}${url.urlCode}`,
          });
        }
      } else {
        res.status(401).json('Invalid long url');
      }
    } catch (err) {
      console.log(err);
      res.status(500).json('Server Error');
    }
  }

  /**
   * 通过短链接获取原始请求url信息
   * @param {Request} req 请求
   * @param {Response} res 响应
   */
  public async getOriginUrl(req: Request, res: Response) {
    try {
      const urlCode = req.query.urlCode as string;
      const url = await this.Model.findOne({ urlCode });
      if (url) {
        res.json({
          originUrl: url.longUrl,
        });
      } else {
        res.status(404).json('No url found');
      }
    } catch (err) {
      res.status(500).json('Server Error');
    }
  }
}
