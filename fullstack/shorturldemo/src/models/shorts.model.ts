import { Sequelize, DataTypes, Model, Optional } from 'sequelize';
import { Short } from '@/interfaces/shorts.interface';

export class ShortModel extends Model<Short> implements Short {
  public id: number;
  public origin: string;
  public shortKey: string;
  public md5: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof ShortModel {
  ShortModel.init(
    {
      id: {
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER,
      },
      origin: {
        allowNull: false,
        type: DataTypes.STRING(2083),
      },
      shortKey: {
        allowNull: false,
        type: DataTypes.STRING(255),
      },
      md5: {
        allowNull: false,
        type: DataTypes.STRING(32),
      }
    },
    {
      tableName: 'shorts',
      sequelize,
    },
  );

  return ShortModel;
}
