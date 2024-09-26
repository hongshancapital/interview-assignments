import { CSSProperties } from 'react';

export interface CarouselProps {
  duration: number;
  dataSource: CarouselItem[];
}

export interface CarouselItem {
  title: string;
  descriptions?: string[];
  image: string;
  theme: 'Darken' | 'Lighten';
}

export interface DonutLinkedMapNode<T> {
  key: number;
  data: T;
  prev: number;
  next: number;
}

export type DonutLinkedMap<T> = Record<number, DonutLinkedMapNode<T>>;

export interface ItemRendererProps extends CarouselItem {
  style?: CSSProperties;
}

export interface IndicatorProps {
  duration: number;
  isCurrent: boolean;
}