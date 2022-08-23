import { createGlobalState } from "./hooks/createGlobalState";

export interface ICarouselState {}

export const useCarouselState = createGlobalState<ICarouselState>({});
