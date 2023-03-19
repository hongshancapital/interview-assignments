import { CarouselProps } from "../type";

export const isIdUniq = (data: CarouselProps["data"]): boolean =>
  new Set(data.map(({ id }) => id)).size === data.length;
