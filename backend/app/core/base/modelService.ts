/**
 * @file service 基类
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { Context, Service } from 'egg';
import * as sequelize from 'sequelize';
import superSequelize from '../../../typings/app/core/modelService';

export default abstract class ModelService<TInstance, TAttributes> extends Service {

  // TAttributes 是完整参数
  // superSequelize.Attributes<TAttributes> 是TAttributes & BizAttributes
  constructor (ctx: Context, public model: sequelize.Model<TInstance, TAttributes>, public schema: superSequelize.DefineAttributes) {
    super(ctx);
  }

  private isEmptyObject(obj: object) {
    return !Object.keys(obj).length;
  }

  public create(values?: TAttributes, options?: sequelize.CreateOptions) {
    return this.model.create(values, options);
  }

  public bulkCreate(records: TAttributes[], options?: sequelize.BulkCreateOptions) {
    return this.model.bulkCreate(records, options);
  }

  // sequelize.FindOptions<TAttributes & BizAttributes>
  public getOne(options: superSequelize.GetOneOptions<superSequelize.Attributes<TAttributes>> = {}) {
    if (!options.where) {
      options.where = {};
    }
    if (this.isEmptyObject(options.where)) {
      throw new sequelize.ValidationError('where option can not be empty option');
    }
    options.raw = true;
    return this.model.findOne<superSequelize.Attributes<TAttributes>>(options);
  }

  public getAll(options: superSequelize.GetListOptions<superSequelize.Attributes<TAttributes>> = {}) {
    if (!options.where) {
      options.where = {};
    }
    if (this.isEmptyObject(options.where)) {
      throw new sequelize.ValidationError('where option can not be empty option');
    }
    options.raw = true;
    return this.model.findAll<superSequelize.Attributes<TAttributes>>(options);
  }

  public update(values: Partial<superSequelize.Attributes<TAttributes>>,
    options: superSequelize.UpdateOptions<superSequelize.Attributes<TAttributes>>) {
    if (!options.where) {
      options.where = {};
    }
    if (this.isEmptyObject(options.where)) {
      throw new sequelize.ValidationError('where option can not be empty option');
    }
    return this.model.update(values, options);
  }
}
