import { CSSProperties, ReactNode } from 'react';
export interface ICarouselProps {
  className?: string;
  style?: CSSProperties;
  duration?: number;
  autoPlay?: boolean;
  children: ReactNode[];
  animationDuration?: number;
}

export interface ISlideProps {
  title: string[];
  description?: string[];
  img: string;
  backgroundColor?: string;
  color?: string;
}

export type TTimeID = ReturnType<typeof setInterval>;
