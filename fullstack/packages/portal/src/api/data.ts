import fetcher from './fetcher';
import { baseApi } from './base';

export const pageAPI = (url: string) => `${baseApi}/shorturl/getshort?url=${url}`
export const getShortUrl = async (url: string) => {
  const { data, statusCode, message } = await fetcher(pageAPI(url));
  return data
}


export const getAllSideData = async () => ({
  // [logoApi]: await getLogoData(),
})