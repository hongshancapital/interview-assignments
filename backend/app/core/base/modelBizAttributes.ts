/**
 * @file model schema统一参数
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

import * as sequelize from 'sequelize';
import superSequelize from '../../../typings/app/core/modelService';

const bizAttributes: superSequelize.DefineAttributes = {
  id: {
    type: sequelize.INTEGER(),
    primaryKey: true,
    autoIncrement: true,
    comment: '自增主键',
    allowUpdate: false,
  },
  updateTime: {
    type: sequelize.DATE(3),
    allowNull: false,
    defaultValue: sequelize.NOW,
    comment: '更新时间',
  },
  createTime: {
    type: sequelize.DATE(3),
    allowNull: false,
    defaultValue: sequelize.NOW,
    comment: '创建时间',
  },
};
export default bizAttributes;
