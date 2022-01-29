import React from 'react';
import Carousel from './carousel';

export type Position = 'left' | 'top' | 'right' | 'bottom';

export interface CarouselItemProps {
  className?: string;
  style?: React.CSSProperties;
  children?: React.ReactNode;
}

export interface CarouselProps {
  className?: string;
  style?: React.CSSProperties;
  /**
   * The first rendering of the marquee is the default rendering of the first few default 0
   */
  defaultIndex?: number;
  /**
   * Duration of each marquee default 3000ms
   */
  duration?: number;
  children?: React.ReactNode;
  /**
   * marquee switch monitoring
   */
  onChange?: (currentIndex: number, beforeIndex: number) => void;
  /**
   * Marquee animation execution time default 500ms
   */
  animationDuration?: number;
  /**
   * dot position default bottom
   */
  dotPosition?: Position;
  /**
   * dot offset default 50
   */
  dotOffset?: number | string;
  dotStyle?: React.CSSProperties;
  /**
   * Whether to enable portrait orientation, not enabled by default
   */
  vertical?: boolean;
}

export interface DotProps {
  active: boolean;
  duration: number;
  dotPosition: Position;
}

export default Carousel;
