/**
 * @file 短连接存储
 * @author zengbaoqing<misterapptracy@gmail.com>
 * @desc  
 */
import { Application } from 'egg';
import * as sequelize from 'sequelize';
import ModelBizAttributes from '../core/base/modelBizAttributes';
import superSequelize from '../../typings/app/core/modelService';

interface CusAttributes {
  shortId: string;
  url: string;
}

// 字段声明
export type Attributes = superSequelize.Attributes<CusAttributes>;

// 实例类声明
export type Instance = superSequelize.Instance<CusAttributes>;

// 实例类声明
export const defineAttributes = {
  ...ModelBizAttributes,
  shortId: {
    type: sequelize.STRING(11),
    allowNull: false,
    comment: '短连接id，全局唯一',
  },
  url: {
    type: sequelize.STRING(2047),
    allowNull: false,
    comment: '长连接',
  },
};

export default (app: Application) => {
  const { model } = app;
  return model.define<Instance, Attributes>('short_url', defineAttributes, {
    freezeTableName: true,
    indexes: [
      {
        unique: true,
        fields: ['shortId'],
      },
    ],
  });
};