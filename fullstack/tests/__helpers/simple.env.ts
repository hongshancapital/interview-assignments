process.env.SL_HOST = 'sl.test.cn'; // 短链接域名

process.env.SL_SERVICE = 'simple'; // 短链接服务类型

process.env.SL_SERVICE_PARAMS =
  '?pg_host=192.168.1.10&pg_port=5432&pg_user=sl&pg_password=password'; // 短链接服务参数

function deleteCache() {
  delete require.cache[require.resolve('../../src/config')];
  delete require.cache[require.resolve('../../src/service/factory')];
}

export { deleteCache };
