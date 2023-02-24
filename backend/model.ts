import { Model, DataTypes, CreateOptions, Sequelize} from 'sequelize';

class ShortDomain extends Model {
  declare id: CreateOptions<number>;
  declare domain: string;
  declare shorten: string;
  declare createdAt: CreateOptions<Date>;
  declare updatedAt: CreateOptions<Date>;
}

const init = (sequelize: Sequelize) => {
  ShortDomain.init(
    {
      id: {
        type: DataTypes.INTEGER.UNSIGNED,
        autoIncrement: true,
        primaryKey: true
      },
      domain: {
        type: new DataTypes.STRING(2000),
        allowNull: false
      },
      shorten: {
        type: new DataTypes.STRING(8),
        allowNull: false
      },
      createdAt: DataTypes.DATE,
      updatedAt: DataTypes.DATE,
    },
    {
      tableName: 'shortdomain',
      sequelize
    }
  ).sync();
}

export default {
  ShortDomain,
  init
};