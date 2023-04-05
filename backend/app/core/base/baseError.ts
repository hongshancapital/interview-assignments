/**
 * @file 基础异常类型
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

export default {
  paramsError(msg?: string) {
    return {
      status: 1001,
      statusInfo: msg || '参数错误',
    };
  },
  serverError(msg?: string) {
    return {
      status: 1002,
      statusInfo: msg || '系统异常',
    };
  },
};
