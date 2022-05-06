export interface CarouselDataType {
  id: string;
  title: string[];
  description?: string[];
  color: string;
  backgroundImg: string;
}

export const ease = {
  ease: "ease",
  "ease-in": "ease-in",
  "ease-out": "ease-out",
  "ease-in-out": "ease-in-out",
  linear: "linear",
} as const;

export type ValueOf<T> = T[keyof T];
