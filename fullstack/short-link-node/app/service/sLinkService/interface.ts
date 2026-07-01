export interface AddShortLinkParams {
  appid: string
  oriUrl: string
}
export interface AddShortLinkReturn {
  slink: string
}
export interface UpdateShortLinkParams {
  slink: string
  appid: string
  oriUrl: string
}
export interface UpdateShortLinkReturn {
  slink: string
  oriUrl: string
}
export interface GetShortLinkParams {
  slink: string
}
export interface GetShortLinkReturn {
  slink: string
  oriUrl: string
}
