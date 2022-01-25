import {CSSProperties, HTMLProps} from "react";

export interface ICarouselProps {
  data: Omit<SlideProps, "activeIndex" | "slideIndex">[];
}

export interface SlideProps {
  /**
   * background image of the slide
   */
  image: string;
  description?: string[];
  title: string[];
  textColor?: string;
  bgColor?: CSSProperties["backgroundColor"];
  /**
   * the scale rate of the image due to different images size
   * could be better to use the same size of the image
   */
  imageScaleRate?: 1 | 2;
  slideIndex: number;
  activeIndex: number;
}

export interface ButtonProps {
  onMouseUp: (index: number) => void;
  onMouseDown: (index: number) => void;
  slideIndex: number;
  forceRefresh?: boolean;
  activeIndex?: number;
}

export interface CarouselConfig {
  /**
   * the time interval of the carousel
   */
  delay?: number;
  width?: CSSProperties["width"];
  height?: CSSProperties["height"];
  /**
   * play the carousel automatically
   */
  autoplay?: boolean
}
