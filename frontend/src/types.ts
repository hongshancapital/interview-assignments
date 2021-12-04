export type IconType = 'iphone' | 'airpods' | 'tablet';

export type CarouselItem = {
  iconType: IconType;
  fontColor?: string;
  backgroundColor?: string;
  title?: string[];
  text?: string[];
};
