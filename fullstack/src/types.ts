export interface IBaseResponse<T = never> {
  success: boolean;
  message?: string;
  detail?: any;
  data: T;
}

export interface IError {
  err: any;
}

export interface IRegisterUrlRequest {
  url: string;
}

