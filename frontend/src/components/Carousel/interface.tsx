import React, { ReactNode } from "react";

export interface CarouselItem {
  id: number | string;
  bgSrc: string;
  // background: string;
  title: ReactNode;
  description?: ReactNode;
  className?: string;
}

export interface CarouselProps {
  data: CarouselItem[];
  duration?: number
}
