import { ReactNode } from "react";

export interface ICarouselProps {
  interval?: number;
  children: ReactNode | ReactNode[];
  transitionDuration?: number;
}

export interface ICarouselState {
  current: number;
  count: number;
  height: number;
  width: number;
}

export interface ICarouselAction {
  type: string;
  payload?: Partial<ICarouselState>;
}

export interface ICarouselMethods {
  prev: () => void;
  next: () => void;
  goTo: (to: number) => void;
}
