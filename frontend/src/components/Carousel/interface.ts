import { CSSProperties } from "react";

export interface CarouselProps {
  items: CarouselItemProps[];
  slideInterval?: number;
  wrapperStyle?: CSSProperties;
}

export interface CarouselItemProps {
  src: string;
  alt?: string;
  title: string[];
  desc?: string[];
  imgHeight?: CSSProperties["height"];
  wrapperStyle?: CSSProperties;
}
export interface CarouselProgressProps {
  count: number;
  active: number;
  slideInterval: number;
}