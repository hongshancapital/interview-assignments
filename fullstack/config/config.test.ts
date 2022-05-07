import { EggAppConfig, PowerPartial } from 'egg';
import database from './database';

export default () => {
  const config = {};
  const bizConfig = {
    sequelize: database.development,
  };

  return {
    ...config,
    ...bizConfig,
  } as PowerPartial<EggAppConfig>;
};
