//@ts-ignore
import urlParse from 'url-parse';

/**
 * check if a param is a valid url
*/
export function isValidUrl(url: string): boolean {
  const parsedUrl = new urlParse(url);
  //ignore only contains domain url situation
  //@ts-ignore
  return parsedUrl.origin && parsedUrl.protocol && (parsedUrl.pathname?.length>1);
}

/**
 * get url host for shortening url
*/
export function getBaseUrl(url: string): string {
  const parsedUrl = new urlParse(url);
  return parsedUrl.origin;
}
