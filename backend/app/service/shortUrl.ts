/**
 * @file 短链接
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
'use strict';
import { Context } from 'egg';
import ModelService from '../core/base/modelService';
import { Attributes, defineAttributes, Instance } from '../model/shortUrl';

export default class ShortUrlService extends ModelService<Instance, Attributes> {

  constructor (ctx: Context) {
    super(ctx, ctx.app.model.ShortUrl, defineAttributes);
  }

}
