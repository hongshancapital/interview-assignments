import { useState, useCallback, useMemo } from "react";

interface CarouselHook {
  (length: number, duration: number, transitionTime: number): {
    curIndex: number;
    minTransitionTime: number;
    stepToNext: () => void;
  };
}

export const useCarousel: CarouselHook = (length, duration, transitionTime) => {
  const [curIndex, setCurIndex] = useState(0);

  const stepToNext = useCallback(() => {
    setCurIndex((prev) => {
      return (prev + 1) % length;
    });
  }, [length]);

  const minTransitionTime = useMemo(() => {
    return Math.min(duration, transitionTime);
  }, [duration, transitionTime]);

  return { curIndex, stepToNext, minTransitionTime };
};
