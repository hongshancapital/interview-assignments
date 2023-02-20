import { ReactNode } from "react";

export interface CarouselItemData {
  url: string;
  alt?: string,
  title?: ReactNode;
  desc?: ReactNode;
  backgroundColor?: string
}
