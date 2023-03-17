import { CarouselProps } from "../type";

export const checkIdUniq = (data: CarouselProps["data"]): boolean =>
  new Set(data.map(({ id }) => id)).size !== data.length;
