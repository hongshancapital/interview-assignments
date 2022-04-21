export interface ICarouselProps {
  delay?: number;
  list: ICarouselItem[];
  isLoop?: boolean;
}

export type ImageType = 'iphone' | 'tablet' | 'airpods';

export interface ICarouselItem {
  backgroundColor?: string;
  color?: string;
  title: string;
  subtitle?: string;
  text: string | string[];
  image: ImageType;
}
