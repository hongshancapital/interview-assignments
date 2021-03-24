/**
 * 配置编译环境和线上环境之间的切换
 *
 * baseUrl: 域名地址
 *
 */

let baseUrl = process.env.VUE_APP_SERVER_URL; // 接口域名

export {baseUrl};
