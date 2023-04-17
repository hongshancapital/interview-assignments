
import urlService from '../services/url'
import stringUtils from '../utils/stringUtils';

/**
 * @description:
 * @param {*} T
 * @return {*}
 */
export const saveUrlCont = async (ctx: any, next:any)  => {
  const longurl = ctx.request.body.url

  let shorturl = await urlService.saveUrl(longurl)
  ctx.body = {
    code : 200,
    msg  : 'success',
    data : {
      shorturl
    }
  }
  return next()
};
/**
 * @description:
 * @param {*} T
 * @return {*}
 */
export const getLongUrlByShortCont = async (ctx: any, next:any)  => {
  let shorturl = ctx.request.query.shorturl
  let longurl = await urlService.getLongUrlByShort(shorturl)
  ctx.body = {
    code : 200,
    msg  : 'success',
    data : longurl
  }
  return next()
};

