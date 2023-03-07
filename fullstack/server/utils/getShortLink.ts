import { CreateShortLinkBody } from '../src/interface'
import url from 'url';
import fnv from 'fnv-plus'
import {NextFunction} from 'express'
interface parseShortLinkResult extends CreateShortLinkBody {
  shortLink: string;
}

const parseShortLinkFromBody: (body: CreateShortLinkBody) => parseShortLinkResult  = (body: CreateShortLinkBody) =>  {
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