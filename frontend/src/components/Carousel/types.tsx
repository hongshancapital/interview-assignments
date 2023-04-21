/**
 * @description: data from api-sliders
 * @return {*}
 */
export interface Slider {
  id: number;
  image: string;
  fontColor: string;
  title?: string;
  subTitle?: string;
}

export interface CarouselProps {
  sliders: Slider[];
  startIndex: number;
  interval: number;
}
