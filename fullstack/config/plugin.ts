import {EggPlugin} from 'egg';

const plugin: EggPlugin = {
  // static: true,
  // nunjucks: {
  //   enable: true,
  //   package: 'egg-view-nunjucks',
  // },
  routerPlus: {
    enable: true,
    package: 'egg-router-plus',
  },
  cors: {
    enable: true,
    package: 'egg-cors'
  },

  /**
   * 数据层
   */
  mongoose: {
    enable: true,
    package: 'egg-mongoose',
  },
  /**
   * apidoc
   */
  swaggerdoc: {
    enable: true,   // 是否启用。
    package: 'egg-swagger-doc', // 指定包名称。
  },

};

export default plugin;
