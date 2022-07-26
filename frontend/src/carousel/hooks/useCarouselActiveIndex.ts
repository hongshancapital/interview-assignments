import React from 'react';
import { useTimeout } from 'usehooks-ts';

export function useCarouselActiveIndex(count: number, durationMs: number, defaultActiveIndex?: number): number {
  const [activeIndex, setActiveIndex] = React.useState(defaultActiveIndex || 0);

  useTimeout(() => {
    setActiveIndex((activeIndex + 1) % count);
  }, durationMs);

  return activeIndex;
}