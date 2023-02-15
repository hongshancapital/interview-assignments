import { useEffect, useState } from 'react';

export type Options = { duration: number; total: number };
export const useCarousel = ({ duration, total }: Options) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    let id = setTimeout(function fn() {
      setCurrentIndex(currentIndex => (currentIndex + 1) % total);
      id = setTimeout(fn, duration);
    }, duration);

    return () => clearTimeout(id);
  }, [duration, total]);

  return { currentIndex };
};
