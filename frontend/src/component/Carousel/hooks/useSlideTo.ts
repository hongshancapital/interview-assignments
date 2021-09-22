

import { useCallback } from 'react';
import { useCarousel } from './useCarousel';

export function useSlideTo(): (nextIndex: number) => void {
  const carousel = useCarousel();

  return useCallback((nextIndex: number) => {
    carousel.slideTo(nextIndex);
  }, []);
}
