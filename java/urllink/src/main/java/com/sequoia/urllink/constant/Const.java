package com.sequoia.urllink.constant;

/**
 * 常量管理
 *
 * @author chenYing
 * @date 2018年09月07日 下午16:44:41
 */
public interface Const {
  /**
   * 运行环境
   */
  String RUN_ENV_PRO = "pro";
  String RUN_ENV_DEV = "dev";
  String RUN_ENV = "run.env";
  /**
   * 公钥私钥
   */
  String KEYSTORE_ALIAS = "fuLuService";
  String KEYSTORE_CIPHER_TYPE = "RSA";
  String KEYSTORE_PWD = "FuLuService+-*/123";
  String KEYSTORE_TYPE = "pkcs12";
  String KEY_STORE_PATH = "keystore.path";
  /**
   * 通行证相关参数
   */
  String FL_GRANT_TYPE = "grant_type";
  String FL_ACCESS_TOKEN = "access_token";
  String FL_CLIENT_ID = "client_id";
  String FL_CLIENT_SECRET = "client_secret";
  String FL_EXPIRATION = "expiration";
  String FL_EXPIRES_IN = "expires_in";
  /**
   * 系统后台管理开关
   */
  String AUTH_INTERCEPTOR = "system.authInterceptor.switch";
  /**
   * 跳转到登录的页面
   */
  String AUTH_LOGIN_INDEX = "auth.login.index";
  /**
   * 日志追踪
   */
  String COMPONENT = "ICH.Bigdata.Http";
  /**
   * jwt
   */
  String JWT_ISSUER = "jwt.issuer";
  String JWT_RPC_SECRET = "jwt.rpc.secret";
  String JWT_SECRET = "jwt.secret";
  String JWT_TOKEN_TYPE = "jwt.token.type";
  String JWT_URL = "jwt.url";
  String JWT_GRANT_TYPE = "client_credentials";
  String JWT_REFRESH_TOKEN = "refresh_token";
  /**
   * 拦截器信息
   */
  String MVC_INTERCEPTORS = "spring.mvc.interceptors";
  String SPRING_APP_NAME = "spring.application.name";
  String SPRING_MVC_EXCLUDE = "exclude";
  String SPRING_MVC_INCLUDE = "include";
  String SPRING_MVC_PRE = "spring.mvc";
  /**
   * 请求权限：头信息
   */
  String TOKEN_SESSION_KEY = "Authorization";
  String TOKEN_TYPE = "token_type";
  /**
   * userinfo
   */
  String USER_SESSION_KEY = "userInfo";
  /**
   * 版本号管理
   */
  String VERSION_1 = "v1";
  /**
   * 解决前后端分离后，项目跨域的问题
   */
  String VIEW_DOMAIN = "view.domain";
}
