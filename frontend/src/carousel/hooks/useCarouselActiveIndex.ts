import React from 'react';
import { useInterval } from 'usehooks-ts';

export function useCarouselActiveIndex(count: number, durationMs: number, defaultActiveIndex: number = 0): number {
  const [activeIndex, setActiveIndex] = React.useState(defaultActiveIndex);

  useInterval(() => {
    if (count > 0) {
      setActiveIndex((activeIndex + 1) % count);
    }
  }, durationMs);

  return activeIndex;
}