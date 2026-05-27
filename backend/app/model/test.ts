/**
 * @file 测试用表
 * @author zengbaoqing<misterapptracy@gmail.com>
 * @desc  
 */
import { Application } from 'egg';
import * as sequelize from 'sequelize';
import ModelBizAttributes from '../core/base/modelBizAttributes';
import superSequelize from '../../typings/app/core/modelService';

interface CusAttributes {
  content: string;
};

// 字段声明
export type Attributes = superSequelize.Attributes<CusAttributes>;

// 实例类声明
export type Instance = superSequelize.Instance<CusAttributes>;

// 实例类声明
export const defineAttributes = {
  ...ModelBizAttributes,
  content: {
    type: sequelize.STRING(63),
    allowNull: true,
    comment: '测试内容',
  }
};

export default (app: Application) => {
  const { model } = app;
  return model.define<Instance, Attributes>('test_data', defineAttributes, {
    freezeTableName: true,
    indexes: [],
  });
};