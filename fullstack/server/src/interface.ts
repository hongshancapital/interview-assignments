// 生成短链接 post 接口请求参数
export interface CreateShortLinkBody {
  longUrl: string;
}

export interface ShortLinkToLongLink {
  shortUrl: string;
}