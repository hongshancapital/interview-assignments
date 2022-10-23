process.env.SL_HOST = 'sl.test.cn'; // 短链接域名

process.env.SL_SERVICE = 'elasticsearch'; // 短链接服务类型

process.env.SL_SERVICE_PARAMS =
  '?es_host=192.168.1.10&es_port=9200&es_user=sl&es_password=password'; // 短链接服务参数

function deleteCache() {
  delete require.cache[require.resolve('../../src/config.ts')];
  delete require.cache[require.resolve('../../src/service/factory.ts')];
}

export { deleteCache };
