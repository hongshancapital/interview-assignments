import { CSSProperties } from "react";

export interface CarouselProps {
  items: CarouselItemProps[];
  imgHeight?: CSSProperties["height"];
  slideInterval?: number;
  wrapperStyle?: CSSProperties;
}

export interface CarouselItemProps {
  src: string;
  alt?: string;
  title: string[];
  desc?: string[];
  imgHeight?: CarouselProps["imgHeight"];
  wrapperStyle?: CSSProperties;
  titleStyle?: CSSProperties;
  descStyle?: CSSProperties;
}
export interface CarouselProgressProps {
  count: number;
  active: number;
  slideInterval: number;
}