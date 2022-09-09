import { CSSProperties } from 'react';

export interface CarouselItem {
  key: string;
  title?: string;
  description?: string;
  illustration?: string;
}

export interface CarouselProps {
  dataSource?: CarouselItem[];
  interval?: number;
  defaultKey?: string;
  className?: string;
  style?: CSSProperties;
}

export type Timer = ReturnType<typeof setInterval>;
