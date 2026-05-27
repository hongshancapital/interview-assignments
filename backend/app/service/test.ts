/**
 * @file 测试版的model service
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
'use strict';
import { Context } from 'egg';
import ModelService from '../core/base/modelService';
import { Attributes, defineAttributes, Instance } from '../model/test';

export default class TestService extends ModelService<Instance, Attributes> {

  constructor (ctx: Context) {
    super(ctx, ctx.app.model.Test, defineAttributes);
  }

}
