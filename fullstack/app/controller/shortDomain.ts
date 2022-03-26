import BaseController from './base';
import {ResourceRoute} from './../decorator/router_register';

/**
 * @controller 短域名服务
 */
export default class shortDomainController extends BaseController {

  /**
   * @summary 接受长域名，返回短域名
   * @description 短域名存储接口：接受长域名信息，返回短域名信息
   * @router get /save/long/url
   * @request query string oriUrl 链接地址 - example:'https://www.bilibili.com/video/BV1TS4y1S75Q?spm_id_from=444.41.0.0'
   * @response 0 successRst 返回结果
   */
  @ResourceRoute('/save/long/url', 'get')
  async saveLongUrl() {
    const result = await this.service.shortDomain.saveLongUrl(this.getQuery());
    this.res({
      data: result,
    });
  }

  /**
   * @summary 接受短域名信息，返回长域名信息
   * @description 短域名读取接口：接受短域名信息，返回长域名信息。
   * @router get /get/long/url/info
   * @request query string oriUrl 链接地址 - example:'https://BJua71cR'
   * @response 0 successRst 返回结果
   */
  @ResourceRoute('/get/long/url/info', 'get')
  async getLongUrlInfo() {
    const result = await this.service.shortDomain.getLongUrlInfo(this.getQuery());
    this.res({
      data: result,
    });
  }

}
