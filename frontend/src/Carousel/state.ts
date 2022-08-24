import { createGlobalState } from "./hooks/createGlobalState";

export interface ICarouselState {
  current: number | null;
  autoPlay?: boolean;
  duration?: number;
  stopAtGesture?: boolean;
}

export const useCarouselState = createGlobalState<ICarouselState>({ current: null });
