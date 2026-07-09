import { DataTypes, Model, Optional } from 'sequelize'
import sequelize from './config'

interface ShortLinkAttributes {
  id: number;
  shortUrl: string;
  originalUrl: string;
  userId?: number;
  machineId?: string;
  createdAt?: Date;
}
export interface ShortLinkInput extends Optional<ShortLinkAttributes, 'id'> {}
export interface ShortLinkOuput extends Required<ShortLinkAttributes> {}

class ShortLink extends Model<ShortLinkAttributes, ShortLinkInput> implements ShortLinkAttributes {
  public id!: number
  public shortUrl!: string
  public originalUrl!: string
  public userId!: number
  public machineId!: string

  // timestamps!
  public readonly createdAt!: Date;
}

ShortLink.init({
  id: {
    type: DataTypes.INTEGER.UNSIGNED,
    autoIncrement: true,
    primaryKey: true,
    comment: "主键id"
  },
  // 默认存储 8 个字符，剩余留作扩展使用
  // host 在 controller 层进行拼接处理
  shortUrl: {
    type: DataTypes.CHAR(12),
    allowNull: false,
    comment: "无host短链，默认存储 8 个字符，剩余留作扩展使用"
  },
  originalUrl: {
    type: DataTypes.STRING(3000),
    allowNull: false,
    comment: "原始链接"
  },
  userId: {
    type: DataTypes.INTEGER.UNSIGNED,
    comment: "用户id"
  },
  machineId: {
    type: DataTypes.CHAR(4),
    comment: "机器id"
  }
}, {
  createdAt: true,
  sequelize,
  paranoid: true,
  indexes: [
    {
      fields: ['shortUrl']
    },
    {
      fields: [{ name: 'originalUrl', length: 100 }],
    },
  ]
})

export default ShortLink