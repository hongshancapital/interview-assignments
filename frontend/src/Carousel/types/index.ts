/** 轮播图卡片 */
export interface ICarouselItem {
  /** 轮播图 唯一标识 */
  key: string;
  /** 轮播时长，默认 3000 */
  timeout?: number;
  children: React.ReactNode;
}

/** 轮播图 props */
export interface IUseCarouselOptions {
  /** 自动滚动 */
  autoPlay?: boolean;
  autoPlayGap?: number;
  /** 轮播图卡片项 */
  items: ICarouselItem[];
  /** 默认开始的位置 */
  defaultActiveKey?: string;
}