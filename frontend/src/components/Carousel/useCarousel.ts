import { useState, useEffect, useRef } from 'react';

export const useCarousel = (count: number, duration: number) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [transitionEndIndex, setTransitionEndIndex] = useState(0);
  const timer = useRef<number>();

  useEffect(() => {
    if (transitionEndIndex > -1) {
      timer.current = window.setTimeout(() => {
        setCurrentIndex((prev) => {
          if (prev === count - 1) {
            return 0;
          }

          return prev + 1;
        });
      }, duration);
    }

    return () => {
      window.clearTimeout(timer.current);
    };
  }, [count, duration, transitionEndIndex]);

  const jumpTo = (index: number) => {
    if (timer.current) {
      window.clearTimeout(timer.current);
    }
    setTransitionEndIndex(-1);
    setCurrentIndex(index);
  };

  return {
    currentIndex,
    transitionEndIndex,
    setTransitionEndIndex,
    jumpTo,
  };
};
