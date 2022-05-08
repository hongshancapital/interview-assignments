declare module 'interface' {

  type TState = 0 | 1;

  interface IUrlMapCache {
    id: number,
    tinyUrl: string,
    originalUrl: string,
  }

  interface IRefreshCache {
    pid: number,
    type: 'GET' | 'SET' | 'DEL',
    key: string | string[],
    value?: IUrlMapCache,
  }

  interface IRefreshBloom {
    pid: number,
    value: JSON,
  }

  interface ICacheClient {
    get: (key: string) => unknown;
    set: (key: string, value: unknown) => void;
    del: (key: string | string[]) => number;
    showAll: () => Map;
  }

  interface IOriginalUrl {
    originalUrl: string
  }

  interface ITinyUrl {
    tinyUrl: string
  }

  interface IRedirectUrl {
    url: string
  }
  interface IParamTinyUrl {
    originalUrl: string,
    creator: string,
    expire?: number,
  }
  interface IDateMap {
    createDate: number,
    expireDate: number,
  }

  interface IInfo extends IDateMap {
    tinyUrl: string,
    originalUrl: string,
    creator: string,
  }
};