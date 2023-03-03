import { putLink, LinkParams, getThenIncrementIndex } from '../service/dao';
import { shortLinkHead } from '../constants';
const { encodeToBase62 } = require('../util');

interface REQ_PARAMS {
  lUrl: string; // 原始长链
}

/**
 * @linkreq 长链转换短链主函数
 * @params REQ_PARAMS 请参考上方REQ_PARAMS定义
 */
module.exports = async function linkreq(params: REQ_PARAMS) {
  const { lUrl } = params || {};
  if (!lUrl) {
    return {
      success: false,
      reason: '非法的url入参',
    };
  }

  // 形成短链，短链形成规则：
  // 短链入参为自增长数值，使用数据库ID作为唯一入参，防止碰撞。使用后自增1
  // 使用BASE62进行编码，长度低于8
  const id = await getThenIncrementIndex();
  const token = encodeToBase62(id);
  console.log('#######', token);

  const daoParams: LinkParams = {
    tag: token,
    short_link: `${shortLinkHead}${token}`,
    page_url: lUrl,
  };
  await putLink(daoParams);

  return {
    success: true,
    shortLink: `${shortLinkHead}${token}`,
  };
}