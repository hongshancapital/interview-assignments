import { createGlobalState } from "./hooks/createGlobalState";

export const DEFAULT_AUTOPLAY = { autoPlay: true, duration: 3000, stopAtGesture: false };

export const useCurrentState = createGlobalState<number | null>(null);
export const useAutoPlayState = createGlobalState<boolean>(true);
export const useAutoPlayDurationState = createGlobalState<number>(3000);
export const useStopAtGestureState = createGlobalState<boolean>(true);
export const useItemsCountState = createGlobalState<number>(0);
export const useInDragState = createGlobalState<boolean>(false);
export const useInTransitionState = createGlobalState<boolean>(false);
