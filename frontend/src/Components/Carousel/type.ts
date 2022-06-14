import type Carousel from './core'
import type { NormalPlugin }  from './core'

export interface CarouselProps {
  interval?: number;
  stepTime?: number;
  dots?: boolean | {className?: string; style?: string};
  dotPosition?: 'left' | 'right' | 'top' | 'bottom';
  dotJump?: boolean;
}

export type PluginFactoryResult<D, P = NormalPlugin> = {
  plugin: P;
  data: D;
}

/** CorePlugin */

export interface CorePluginOpt {
  interval: number;
  stepTime: number;
  setTranslate: (index: number) => void;
}

export interface CorePluginResult {
  getTransitionEnd: () => () => void;
  setInterval: (interval: number) => void;
  setStepTime: (time: number) => void;
}

/** Dot */
export interface DotPluginOpt {
  useDot: boolean;
  onChange(progress: number): void;
}

export interface DotProps {
  useDot: boolean;
  dotJump: boolean;
  carousel: Carousel;
}

export interface DotPluginResult {
  getCurrent(): number;
  setUseDot(enable: boolean): void;
}

export interface DotResult {
  onClick(index: number): void;
  getProgress(index: number): number
}

// dot plugin