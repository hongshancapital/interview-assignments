import { CreateShortLinkBody } from '../src/interface'
import url from 'url';
import fnv from 'fnv-plus'
interface ParseShortLinkResult extends CreateShortLinkBody {
  shortLink: string;
}

/**
   * @description: 解析body，拿到长链接，并生成短链接
   * @param {CreateShortLinkBody} body
   * @return {ParseShortLinkResult}
*/
const parseShortLinkFromBody: (body: CreateShortLinkBody) => ParseShortLinkResult  = (body: CreateShortLinkBody) =>  {
  const { longLink } = body;
  let shortLink: string = '';

  const parsedUrl = url.parse(longLink);
  const baseUrl = `${parsedUrl?.protocol}//${parsedUrl?.host}`
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