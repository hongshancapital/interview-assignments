import { CreateShortLinkBody } from '../src/interface'
import url from 'url';
import fnv from 'fnv-plus'

interface parseShortLinkResult extends CreateShortLinkBody {
  shortLink: string;
}

const parseShortLinkFromBody: (body: CreateShortLinkBody) => parseShortLinkResult  = (body: CreateShortLinkBody) =>  {
  const { longLink } = body;
  const parsedUrl = url.parse(longLink);
  const baseUrl = `${parsedUrl?.protocol}//${parsedUrl?.host}`
  // 生成唯一id
  const id: String = fnv.fast1a32hex(longLink);
  // 生成短链
  const shortLink = `${baseUrl}/${id}`
  return {
    shortLink,
    longLink
  }
}

export default parseShortLinkFromBody;