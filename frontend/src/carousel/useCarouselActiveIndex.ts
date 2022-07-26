import React from 'react';
import { useInterval } from 'usehooks-ts';

export function useCarouselActiveIndex(count: number, durationInMs: number, defaultActiveIndex?: number): number {
  const [activeIndex, setActiveIndex] = React.useState(defaultActiveIndex || 0);

  useInterval(() => {
    setActiveIndex((activeIndex + 1) % count);
  }, durationInMs);

  return activeIndex;
}