/**
 * Restful 响应类
 *
 * @class
 *
 * @author microld
 *
 * @version 1.0.0
 *
 * @public
 */
export default class RestResponse<T> {

  public static ok<T>(data: T): RestResponse<T>;
  public static ok<T>(data: T, code: number): RestResponse<T>;
  public static ok<T>(data: T, message: string): RestResponse<T>;
  /**
   * 成功响应
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param data - 数据
   * @param code - 状态码
   * @param message - 响应消息
   *
   * @public
   *
   * @example
   *
   * RestResponse.ok(null);
   * // => { code: 0, message: 'success', data: null }
   *
   * @returns RestResponse 实例
   */
  public static ok<T>(data: T, code: number | string = 0, message: string = 'success'): RestResponse<T> {
    if (typeof code === 'string') {
      message = code;
      code = 0;
    }

    return new this<T>({ data, code, message });
  }

  public static failure<T>(message: string): RestResponse<T>;
  public static failure<T>(code: number, message: string): RestResponse<T>;

  /**
   * 失败响应
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param code - 状态码
   * @param message - 响应消息
   *
   * @public
   *
   * @example
   *
   * RestResponse.failure(-1, 'failure');
   * // => { code: -1, message: 'failure', data: null }
   *
   * @returns RestResponse 实例
   */
  public static failure<T = null>(code: number | string, message?: string) {
    if (typeof code === 'string') {
      message = code;
      code = -1;
    }

    return new this<T>({ code, message, data: null });
  }

  /**
   * 状态码
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   */
  public code: number;

  /**
   * 响应消息
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   */
  public message: string;

  /**
   * 返回数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   */
  public data: T;

  /**
   * Restful Response 构造函数
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @constructor
   *
   * @public
   */
  public constructor(partial?: Partial<RestResponse<T>>) {
    Object.assign(this, partial);
  }

  /**
   * 设置状态码
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   *
   * @example
   *
   * RestResponse.ok('ok').setCode(200);
   * // => { code: 200, message: 'success', data: 'ok' }
   */
  public setCode(code: number): RestResponse<T> {
    this.code = code;
    return this;
  }

  /**
   * 设置响应消息
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   *
   * @example
   *
   * RestResponse.ok(null).setMessage("ok");
   * // => { code: 0, message: 'ok', data: null }
   */
  public setMessage(message: string): RestResponse<T> {
    this.message = message;
    return this;
  }

  /**
   * 设置返回数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   *
   * @example
   *
   * RestResponse.ok(null).setData("ok");
   * // => { code: 0, message: 'success', data: 'ok' }
   *
   * @returns this
   */
  public setData(data: T): RestResponse<T> {
    this.data = data;
    return this;
  }
}
