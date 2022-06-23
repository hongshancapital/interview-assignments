import axios from 'axios'
import { ShortUrl } from '../models/shortUrl';
import { StatusCode, ResponseData } from '../models/http';

export enum Urls {
  getShorter = "/api/shorturls",
  createShorter = "/api/shorturls",
}

export async function createShortUrl(long_url: string): Promise<ShortUrl> {
  const res = await axios.post<ResponseData<ShortUrl>>(`${Urls.createShorter}`, { long_url })
  if (res.status !== StatusCode.Success) {
    throw res.data.message;
  }
  return res.data.payload as ShortUrl;
}