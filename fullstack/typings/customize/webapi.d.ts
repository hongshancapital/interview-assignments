declare module 'web-api' {
  interface IPaging {
    offset: number;
    limit: number;
    total: number;
    hasMore?: boolean
  }
  interface IResponse<T> {
    // 成功状态
    success: boolean;
    // 数据集
    data: T;
  }
  interface IPageResponse<T> {
    // 成功状态
    success: boolean;
    // 数据集
    data: T[];
    paging: IPaging
  }

  interface IErrorResponse {
    success: boolean;
    error: {
      code: number
      message: string
    }
  }

  interface PagingItf {
    offset: number;
    limit: number;
  }

  interface PageResponseItf<T> {
    // 数据集
    data: T[];
    paging: IPaging
  }
}