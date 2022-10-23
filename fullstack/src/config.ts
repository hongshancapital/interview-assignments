export const SL_HOST = process.env.SL_HOST || 'sl.cn'; // 短链接域名

/**
 * 根据情况选择提供的服务
 * 可配置单库单表、单库多表（ID分表）、多库、ELASTICSEARCH、MONGO、多节点文件存储等
 */
type SL_SERVICE_T = 'simple' | 'elasticsearch' | 'file' | string;

export const SL_SERVICE: SL_SERVICE_T = process.env.SL_SERVICE || 'simple'; // 短链接服务类型

export const SL_SERVICE_PARAMS =
  process.env.SL_SERVICE_PARAMS || '?pg_host=127.0.0.1&pg_port=5432&pg_user=sl&pg_password=password'; // 短链接服务参数
