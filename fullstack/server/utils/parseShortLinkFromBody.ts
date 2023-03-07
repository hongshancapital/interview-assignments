import { Request } from 'express';
import url,{UrlWithStringQuery} from 'url';
import fnv from 'fnv-plus'

/**
   * @description: 解析body，拿到长链接，并生成短链接
   * @param {CreateShortLinkBody} body
   * @return {ParseShortLinkResult}
*/
const parseShortLinkFromBody: (body: Request["body"]) => ParseShortLinkResult  = (body: Request["body"]) =>  {
  const { longLink } = body;
  let shortLink: string = '';

  const parsedUrl: UrlWithStringQuery = url.parse(longLink);
  const baseUrl: string = `${parsedUrl?.protocol}//${parsedUrl?.host}`
  // 生成唯一id
  const id: String = fnv.fast1a32hex(longLink);
  // 生成短链
  shortLink = `${baseUrl}/${id}`

  return {
    shortLink: encodeURIComponent(shortLink),
    longLink: encodeURIComponent(longLink)
  }
}

export default parseShortLinkFromBody;