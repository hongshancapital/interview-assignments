import { Sequelize, DataTypes, Model } from "sequelize";
import sequelize from "./db";


export class ShortModel extends Model {
  public short_url!: string;
  public original_url!: string;
}

ShortModel.init({
  id: {
    type: DataTypes.INTEGER.UNSIGNED,
    autoIncrement: true,
    primaryKey: true,
    comment: "主键id"
  },
  short_url: {
    type: DataTypes.CHAR(10),
    allowNull: false,
    comment: "8位短链"
  },
  original_url: {
    type: DataTypes.STRING(1000),
    allowNull: false,
    comment: "原始链接"
  }
}, {
  version: true,
  createdAt: 'create_at',
  updatedAt: 'update_at',
  sequelize,
  tableName: 'short',
  indexes: [
    {
      unique: true,
      name: 'idx_short',
      fields: ['short_url']
    }
  ]
});

export default ShortModel;