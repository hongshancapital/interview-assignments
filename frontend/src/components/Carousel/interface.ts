import { CSSProperties } from "react";

export interface CarouselProps {
  items: CarouselItemProps[];
  slideInterval?: number;
  autoPlay?: boolean;
}

export interface CarouselItemProps {
  src: string;
  alt?: string;
  title: string[];
  desc?: string[];
  imgHeight?: CSSProperties["height"];
  wrapperStyle?: CSSProperties;
}
export interface CarouselIndicatorProps {
  count: number;
  active: number;
  slideInterval: number;
  handleClick?: (index: number) => void;
}