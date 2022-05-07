import { EggAppConfig, PowerPartial } from 'egg';
import database from './database';

export default (): any => {
  const config = {};

  const bizConfig = {
    sequelize: database.development,
    redisConfig: {
      host: '127.0.0.1',
      port: 6379,
    },
  };

  return {
    ...config,
    ...bizConfig,
  } as PowerPartial<EggAppConfig>;
};
