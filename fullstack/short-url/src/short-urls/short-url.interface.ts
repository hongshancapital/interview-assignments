import { HashId, NumberId } from '../utils/short-id-converter';

export interface ShortUrlCreate {
  url: string
}

export interface ShortUrl extends ShortUrlCreate{
  id: NumberId
  shortId: HashId
}