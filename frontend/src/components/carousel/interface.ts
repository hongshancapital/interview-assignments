import React from "react";

export interface CarouselProps {
  height: number;
  duration: number;
  autoplay?: boolean;
  className?: string;
  style?: React.CSSProperties;
}