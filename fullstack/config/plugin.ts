import { EggPlugin } from 'egg';

const plugin: EggPlugin = {
  static: true,
  sequelize: {
    enable: true,
    package: 'egg-sequelize',
  },

  session: {
    enable: true,
    package: 'egg-session',
  },
};

export default plugin;
