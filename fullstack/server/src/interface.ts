// 生成短链接 post 接口请求参数
export interface CreateShortLinkBody {
  longLink: string;
}

export interface ShortLinkToLongLink {
  shortLink: string;
}