'use strict';

import { Context } from "egg";

const STATUS_ERROR_CODE = 500;
const STATUS_ERROR_MESSAGE = '服务器错误';
class JsonResponse {
  ctx: Context;
  constructor(ctx) {
    this.ctx = ctx;
  }

  success(data) {
    this.ctx.set('Content-Type', 'application/json');
    this.ctx.body = {
      code: 0,
      message: '成功',
      data,
    };
  }

  error(code = STATUS_ERROR_CODE, message = STATUS_ERROR_MESSAGE, data = []) {
    this.ctx.set('Content-Type', 'application/json');
    if (typeof code === 'object') {
      try {
        message = code['message'];
        code = code['code'] || STATUS_ERROR_CODE;
      } catch (e) {
        code = STATUS_ERROR_CODE;
        message = STATUS_ERROR_MESSAGE;
      }
    } else if (typeof code === 'string') {
      message = code;
      code = STATUS_ERROR_CODE;
    }
    code = Number.isInteger(code) && code !== 0 ? code : STATUS_ERROR_CODE;
    message = typeof message === 'string' ? message : STATUS_ERROR_MESSAGE;
    this.ctx.body = { code, message, data };
    // 设置状态码，egg会在全局错误处理的地方把状态码设置成500，但我们希望用200
    this.ctx.status = 200;
  }
}

const _JsonResponse = Symbol('Context#jsonResponse');

module.exports = {
  /**
   * 添加jsonResponse
   */
  get jsonResponse() {
    if (!this[_JsonResponse]) {
      this[_JsonResponse] = new JsonResponse(this);
    }
    return this[_JsonResponse];
  },

};
