import {Service} from 'egg';

export default class BaseService extends Service {

  getRepo() {
    return this.ctx.repo;
  }

  getModel() {
    return this.ctx.model;
  }

  /**
   * 获取Helper
   */
  getHelper() {
    return this.ctx.helper;
  }

}
