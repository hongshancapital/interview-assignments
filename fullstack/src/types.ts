export interface BaseResponse<T = any> {
  success: boolean;
  data?: T;
  errMsg?: string;
}

export interface GetUrlReq {
  shortUrl: string;
}

export interface GetUrlRes {
  longUrl: string | undefined;
}

export interface CreateUrlReq {
  longUrl: string;
}

export interface CreateUrlRes {
  shortUrl: string;
}
