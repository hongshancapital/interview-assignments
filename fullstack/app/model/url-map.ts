import { Model, DataTypes, Optional } from 'sequelize';
import { Application } from 'egg';
import { TState } from 'interface';

export interface UrlMapAttr {
  id: number;
  tinyUrl: string;
  originalUrl: string;
  creator: string;
  createDate: number;
  expireDate: number;
  state: TState;
}

export interface UrlMapCreationAttr extends Optional<UrlMapAttr, 'id' | 'state'> { }
export interface UrlMapModel extends
  Model<UrlMapAttr, UrlMapCreationAttr>,
  UrlMapAttr { }

export default (app: Application) => {
  const { model } = app;

  const ModelInstance = model.define<UrlMapModel, UrlMapCreationAttr>(
    'url_mapping',
    {
      id: {
        type: DataTypes.BIGINT,
        primaryKey: true,
        autoIncrement: true,
      },
      tinyUrl: {
        type: DataTypes.STRING(8),
        allowNull: false,
        unique: true,
      },
      originalUrl: {
        type: DataTypes.TEXT,
        allowNull: false,
      },
      creator: {
        type: DataTypes.TEXT,
        allowNull: false,
      },
      createDate: {
        type: DataTypes.DATE,
        allowNull: false,
      },
      expireDate: {
        type: DataTypes.DATE,
        allowNull: false,
      },
      state: {
        type: DataTypes.TINYINT(),
        allowNull: false,
        defaultValue: 0,
      },
    },
  );

  return ModelInstance;
};
