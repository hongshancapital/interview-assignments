import BaseService from './base';
import * as moment from 'moment';
import * as _ from 'lodash';
const constant = require('../common/constant');


const Utils = require('../utils/commonValidationUtils');

/**
 * 短链接服务
 */
export default class shortDomainService extends BaseService {

  /**
   * 存长域名，返回短域名
   * @param params query 对象
   */
  async saveLongUrl(params: any) {
    const ShortUrl = this.getModel().ShortUrl;

    //判断是否存在，返回
    const one = await ShortUrl.findOne({
      oriUrl: params.oriUrl,
      state: constant.STATE.EFFECT
    });
    if(!_.isEmpty(one)){
      return one;
    }

    //生成数据库中唯一的短链接
    const only_ShortUrl_code = await this._generateWord(Utils.generateWordCode(this.config.generateShortLength));

    return await new ShortUrl({
      oriUrl: params.oriUrl,
      shortUrl: `${this.config.baseDomain}${only_ShortUrl_code}`,
      expireTime: moment().add(this.config.shortDomain_effectTime, 's'),
    }).save();
    // return await shortUrl.save();
  }

  /**
   * 接受短域名信息，返回长域名信息
   * @param params query 对象
   */
  async getLongUrlInfo(params: any) {
    const ShortUrl = this.getModel().ShortUrl;

    const hasOne = await ShortUrl.findOne({
      shortUrl: params.oriUrl,
      state: constant.STATE.EFFECT
    });

    return hasOne;
  }

  /**
   * 生成自动6位数
   * @param code 随机字符串
   * @returns {Promise<*>} 返回数据库没有的6位字母
   * @private
   */
  async _generateWord(code: string) {
    const ShortUrl = this.getModel().ShortUrl;

    const one = await ShortUrl.find({
      shortUrl: code
    });

    if (_.isEmpty(one)) {
      return code;
    } else {
      const newCode = Utils.generateWordCode(this.config.bizConfig.generateShortLength);
      return await this._generateWord(newCode);
    }
  }

}
