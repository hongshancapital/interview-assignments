import { ICarouselItem } from "./components/CarouselItem/type";

export interface ICarouselProps {
  list?: ICarouselItem[];
  stayTime?: number;
  changeTime?: number;
}
