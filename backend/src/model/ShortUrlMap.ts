import sequelize from './db'
import { DataTypes } from 'sequelize'

const ShortUrlMap = sequelize.define(
  'ShortUrlMap',
  {
    id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      autoIncrement: true,
      primaryKey: true,
    },
    short_url: DataTypes.STRING,
    long_url: DataTypes.STRING,
    create_time: DataTypes.DATE,
  },
  {
    tableName: 'short_url_map',
    timestamps: false,
  }
)

export default ShortUrlMap
