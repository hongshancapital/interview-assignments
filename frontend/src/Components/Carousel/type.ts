import type React from 'react'

export interface CarouselProps {
  interval?: number;
  stepTime?: number;
  dots?: boolean | {className?: string; style?: string};
  dotPosition?: 'left' | 'right' | 'top' | 'bottom';
  dotJump?: boolean;
  dragable?: boolean;
  roll?: boolean;
}

// carousel hook
export enum CarouselStatus {
  waiting,
  jumping,
  pause,
}
export interface CoreProps {
  interval: number;
  length: number;
}

export interface CoreResult {
  status: CarouselStatus;
  target: number;
  current: number;
  transitionEnd(): void;
  jump(index: number): void;
  pause(): void;
  resume(index: number): void;
}

// dot hook

export type DotProps = [
  {
    enableDot: boolean;
    dotJump: boolean;
    time: number;
  },
  Omit<CoreResult, 'transitionEnd' | 'pause' | 'resume'>
]

export interface DotResult {
  getProgress(index: number): number;
}

// dragger hook

export type DraggerProps = [
  {
    enableDrag: boolean;
    length: number;
  },
  Pick<CoreResult, 'pause' | 'resume'>
]

export interface DraggerResult {
  events: {
    onMouseDown?(e: React.MouseEvent): void;
    onMouseUp?(e: React.MouseEvent): void;
    onMouseMove?(e: React.MouseEvent): void;
    onMouseLeave?(e: React.MouseEvent): void;
  },
  translate: number | null,
}
