import { DataTypes } from '@sequelize/core';
import { sequelize } from '../libs/db-connection';

const shortUrlModel = sequelize.define(
  'shortUrlModel',
  {
    longUrl: {
      type: DataTypes.STRING,
      allowNull: false,
      field: 'long_url',
    },
  },
  {
    tableName: 'short_url_tab',
    timestamps: false, // no need the auto added createAt & updateAt
  }
);

export { shortUrlModel };
