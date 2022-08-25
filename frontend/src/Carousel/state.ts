import { createGlobalState } from "./hooks/createGlobalState";

export interface ICarouselState {
  current: number | null;
  autoPlay?: boolean;
  duration?: number;
  stopAtGesture?: boolean;
  inDrag: boolean;
  direction: "vertical" | "horizontal";
  count: number;
}

export const DEFAULT_AUTOPLAY = { autoPlay: true, duration: 1000, stopAtGesture: false };

export const useCarouselState = createGlobalState<ICarouselState>({
  current: null,
  inDrag: false,
  direction: "horizontal",
  count: 0,
  ...DEFAULT_AUTOPLAY,
});
