import { createContext } from "react";
import { ICarouselState, ICarouselMethods } from "./interface";

export const CarouselContext = createContext<ICarouselState | null>(null);

export const CarouselDispatchContext = createContext<ICarouselMethods | null>(
  null
);
