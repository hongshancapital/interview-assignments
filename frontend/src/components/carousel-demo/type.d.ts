// 走马灯数据类型
export interface ICarouselItem {
  id: number;
  titles?: string[];
  contents?: string[];
  image: string;
  titleColor?: string;
  contentColor?: string;
  bgColor: string;
}
