import {EggAppConfig, EggAppInfo, PowerPartial} from 'egg';

export default (appInfo: EggAppInfo) => {
  // eslint-disable-next-line
  const config = {} as PowerPartial<EggAppConfig>;

  // override config from framework / plugin
  // use for cookie sign key, should change to your own and keep security
  config.keys = appInfo.name + '_1648268426501_7815';

  // 安全设置
  (config.security = {
    csrf: {
      enable: false,
    },
  });

  config.cookieMaxAge = 1000 * 3600 * 24;


  config.i18n = {
    // 默认语言，默认 "en_US"
    defaultLocale: 'zh-CN',
    // URL 参数，默认 "locale"
    queryField: 'locale',
    // Cookie 记录的 key, 默认："locale"
    cookieField: 'locale',
    // Cookie 的 domain 配置，默认为空，代表当前域名有效
    // @ts-ignore
    cookieDomain: '',
    // Cookie 默认 `1y` 一年后过期， 如果设置为 Number，则单位为 ms
    cookieMaxAge: '1y',
  };

  config.bodyParser = {
    formLimit: '10mb',
    jsonLimit: '10mb',
    textLimit: '10mb'
    // 值的大小可以根据自己的需求修改
  };

  // add your egg config in here
  config.middleware = [
    'errorMiddleware',
    'xssMiddleware'
  ];

  /**
   * 日志切片
   */
  config.logrotator = {
    filesRotateByHour: [],
    hourDelimiter: '-',
    filesRotateBySize: [],
    // Max file size to judge if any file need rotate
    maxFileSize: 50 * 1024 * 1024,
    // pieces rotate by size
    maxFiles: 10,
    // time interval to judge if any file need rotate
    rotateDuration: 60000,
    // keep max days log files, default is `31`. Set `0` to keep all logs
    maxDays: 365,
  };


  config.mongoose = {
    client: {
    
      options: {
        // @ts-ignore
        keepAlive: 1,
        poolSize: 10,
        useUnifiedTopology: true,
        useFindAndModify: false,
        // promiseLibrary: require('q').Promise
      }
    },
  };

  config.view = {
    defaultViewEngine: 'nunjucks',
    mapping: {
      '.html': 'nunjucks',
    },
  };

  // egg-swagger-doc 配置信息。
  config.swaggerdoc = {
    dirScanner: './app/controller', // 配置自动扫描的控制器路径。
    // 接口文档的标题，描述或其它。
    apiInfo: {
      title: 'eggjs-ts-api',  // 接口文档的标题。
      description: 'demo项目',   // 接口文档描述。
      version: '1.0.0',   // 接口文档版本。
    },
    schemes: ['http', 'https'], // 配置支持的协议。
    consumes: ['application/json'], // 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html。
    produces: ['application/json'], // 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回。
    securityDefinitions: {  // 配置接口安全授权方式。
    },
    enableSecurity: false,  // 是否启用授权，默认 false（不启用）。
    // enableValidate: true,    // 是否启用参数校验，默认 true（启用）。
    routerMap: true,    // 是否启用自动生成路由，默认 true (启用)。
    enable: true,   // 默认 true (启用)。
  };

  // 跨域设置
  (config.cors = {
    origin: '*', // 访问白名单
    allowMethods: 'GET,HEAD,PUT,POST,DELETE,PATCH',
  });


  //------------------------------业务配置-----------------------------//
  // add your special config in here
  const bizConfig = {
    baseDomain: 'https://',   //基础的url前缀
    generateShortLength: 8,   //生成的随机字符串长度
    shortDomain_effectTime: 60 * 60 * 24 * 7,  //mongo超时默认7天
  };

  // the return config will combines to EggAppConfig
  return {
    ...config,
    ...bizConfig,
  };
};
