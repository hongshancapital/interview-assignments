import { CSSProperties } from "react";

export interface ICarouselItem {
  titleList?: string[];
  descList?: string[];
  style?: CSSProperties;
}

export interface ICarouselItemProps {
  data?: ICarouselItem;
}
