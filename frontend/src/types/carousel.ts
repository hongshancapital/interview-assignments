export interface ICarouselItem {
  key: string;
  title: string; // 标题
  context?: Array<string>; // 正文内容
  img: String; // 背景图片
  color?: string; // 字体颜色
}
