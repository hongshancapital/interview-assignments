import { CSSProperties, ReactNode } from 'react';

export interface CarouselProps {
  className?: string;
  style?: CSSProperties;
  autoplay?: boolean;
  indicator?: ReactNode;
  interval?: number;
  direction?: 'horizontal';
  effect?: 'slide';
  onChange?: (index: number) => void;
}

export interface CarouselItemProps {
  className?: string;
  style?: CSSProperties;
}

export interface InternalCarouselItemProps {
  index?: number;
  activeIndex?: number;
}

export interface CarouselIndicatorProps {
  className?: string;
  style?: CSSProperties;
  shape?: 'line';
}

export interface InternalIndicatorProps {
  slidesLength?: number;
  activeIndex?: number;
  slideTo?: (idx: number) => void;
}
