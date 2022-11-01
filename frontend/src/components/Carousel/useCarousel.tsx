import { useState, useRef, useEffect } from "react";

interface UseCarouselProps {
  itemsLength: number;
  slideInterval: number;
  defaultActiveIndex?: number;
}

const useCarousel = (options: UseCarouselProps) => {
  const { itemsLength, slideInterval, defaultActiveIndex = 0 } = options;
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  const timer = useRef<NodeJS.Timer>();

  useEffect(() => {
    timer.current = setInterval(() => {
      setActiveIndex((index) => (index + 1) % itemsLength)
    }, slideInterval);

    return () => {
      timer.current && clearInterval(timer.current);
    };
  }, [itemsLength, slideInterval, defaultActiveIndex]);

  return activeIndex;
};

export default useCarousel;
