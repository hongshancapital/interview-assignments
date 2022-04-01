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
  triggerType?: 'click' | 'hover';
  position?: 'bottom' | 'top' | 'left' | 'right' | 'outer';
  shape?: 'line' | 'dot';
}

export interface InternalIndicatorProps {
  slidesLength?: number;
  activeIndex?: number;
  interval?: number;
  autoplay?: boolean;
  slideTo?: (idx: number) => void;
}
