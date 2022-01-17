export const ShortUrlKeys = 'POIUYTREWQAZXSDCVFGBNHJKMLzmx9ncb8v7al6skd5jfh4gp3oi2uyq1wer0t_'
export interface GenerateUrlParam {
  originUrl: string;
}
export interface ShortUrlData {
  id?: number;
  originUrl?: string;
  shortUrl?: string;
  isDelete?: boolean;
}