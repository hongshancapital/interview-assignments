/**
 * @file 插件配置
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { EggPlugin } from 'egg';

const plugin: EggPlugin = {
  redis: {
    enable: true,
    package: 'egg-redis',
  },
  sequelize: {
    enable: true,
    package: 'egg-sequelize',
  },
  session: false,
};

export default plugin;
