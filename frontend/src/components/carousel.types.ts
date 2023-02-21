export type SlideType = {
  bgColor: string;
  textColor?: string;
  title: string;
  text: string;
  img: string;
  imgSize: 'l' | 'm' | 's';
};

export interface CarouselProps {
  slides: SlideType[];
  interval?: number;
}