import type React from 'react'
import type Carousel from './core'
import type { CarouselHooks as Hooks } from './core'

export interface CarouselProps {
  interval?: number;
  stepTime?: number;
  dots?: boolean | {className?: string; style?: string};
  dotPosition?: 'left' | 'right' | 'top' | 'bottom';
  dotJump?: boolean;
  dragable?: boolean;
  roll?: boolean;
}

export type PluginFactoryResult<D, P = Hooks> = {
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
  transitionEnd: () => void;
  setInterval: (interval: number) => void;
  setStepTime: (time: number) => void;
}

/** Dot */
export interface DotPluginOpt {
  enableDot: boolean;
  onChange(progress: number): void;
  frameTime: number;
}

export interface DotProps {
  enableDot: boolean;
  dotJump: boolean;
  carousel: Carousel;
}

export interface DotPluginResult {
  getCurrent(): number;
  setEnableDot(enable: boolean): void;
  clean(): void;
}

export interface DotResult {
  getProgress(index: number): number;
}

/** Dragger */
export interface DraggerProps {
  enableDrag: boolean;
  carousel: Carousel;
}

export interface DraggerResult {
  events: {
    onMouseDown(e: React.MouseEvent): void;
    onMouseUp(e: React.MouseEvent): void;
    onMouseMove(e: React.MouseEvent): void;
    onMouseLeave(e: React.MouseEvent): void;
  },
  translate: number | null,
}
