import { getLink } from '../service/dao';

interface REQ_PARAMS {
  sLink: string; // 短链
}

/**
 * @linkreq 长链转换短链主函数
 * @params REQ_PARAMS 请参考上方REQ_PARAMS定义
 */
module.exports = async function querylink(params: REQ_PARAMS) {
  const { sLink } = params || {};
  if (!sLink) {
    return {
      success: false,
      reason: '非法的url入参',
    };
  }

  const tag: string = sLink.substring(sLink.lastIndexOf('/'));
  const page_link: string =  await getLink(tag);

  return {
    success: true,
    page_link,
  };
}