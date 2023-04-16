import UrlModel from '../models/url'
import stringUtils from '../utils/stringUtils';
import config from 'config'



/**
 * @description:
 * @param {longurl} stirng
 * @return {*}
 */
const saveUrl = async (longurl: string) => {
  // 检查数据库是否有 longurl
  let existedUrl = await UrlModel.findOne({url: longurl},{shorturl:1, _id: 0})
  if (existedUrl) {
    return existedUrl.shorturl;
  }
  const count = await UrlModel.count();
  const SERVER_URL = config.get<string>('SERVER_URL')
  const shorturl = `${SERVER_URL}/${stringUtils.getStr62ByNumber(count)}`;
  //  入库
  let urlItem = await UrlModel.create({
    shorturl: shorturl,
    url: longurl,
    createTime: new Date()
  })
  //  返回shorturl
  return urlItem.shorturl;
};
/**
 * @description:
 * @param {shorturl} stirng
 * @return {*}
 */
const getLongUrlByShort = async (shorturl: string) => {
  let itemUrl = await UrlModel.findOne({shorturl: shorturl})
  if(itemUrl){
    return itemUrl.url
  }
  return ""
};
export default {saveUrl, getLongUrlByShort}

