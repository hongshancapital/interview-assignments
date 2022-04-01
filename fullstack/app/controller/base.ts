import {Controller} from 'egg';
import {IHelper} from '../../typings/app';

export interface ResOp {
  data?: any;
  code?: number;
  message?: string;
}

/**
 * @apiDefine BaseRes
 * @apiSuccess {Number} code 错误码，成功则返回200
 * @apiSuccess {String} message 错误信息，成功则返回success
 * @apiSuccess {Object} data 返回的数据
 */

/**
 * @apiDefine Auth
 * @apiHeader {String} Authorization 管理员登陆Token
 */

/**
 * @apiDefine Page
 * @apiSuccess {Array} data.list 查询数据列表
 * @apiSuccess {Object} data.pagination 分页信息
 * @apiSuccess {Number} data.pagination.page 当前页数
 * @apiSuccess {Number} data.pagination.size 限制个数
 * @apiSuccess {Number} data.pagination.total 总数量
 */

export default abstract class BaseController extends Controller {

  /**
   * 获取Query
   */
  protected getQuery(): any {
    return this.ctx.request.query;
  }

  /**
   * 获取Body
   */
  protected getBody(): any {
    return this.ctx.request.body;
  }

  /**
   * 获取Router上的参数
   */
  protected getParams(): any {
    return this.ctx.params;
  }

  /**
   * 获取Helper
   */
  protected getHelper(): IHelper {
    return this.ctx.helper;
  }

  /**
   * 返回数据
   * @param op 返回配置，返回失败需要单独配置
   */
  protected res(op?: ResOp): void {
    this.ctx.set('Content-Type', 'application/json');
    this.ctx.body = {
      data: op?.data ?? null,
      code: op?.code ?? 200,
      message: op?.code ? this.ctx.helper.getErrorMessageByCode(`${op!.code}`) || op?.message || 'unknown error' : op?.message || 'success',
    };
  }

}
