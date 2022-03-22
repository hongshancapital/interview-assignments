import fetcher from './fetcher';
import { appId, baseApi } from './base';

export const logoApi = `${baseApi}/app/page/configs?where=${JSON.stringify({
  "position":"logo_slogen",
  "status": 1,
})}`
export const getLogoData = async () => {
  const { data } = await fetcher(logoApi);
  return data
}

export const getAllSideData = async () => ({
  [logoApi]: await getLogoData(),
})