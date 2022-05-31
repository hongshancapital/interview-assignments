import { useState } from "react";

type IParams = {
  count: number;
  defaultActiveIndex: number;
};

const useCarousel = ({ count, defaultActiveIndex }: IParams) => {
  const [activeIndex, setActiveIndex] = useState(() => {
    return defaultActiveIndex < 0
      ? 0
      : defaultActiveIndex > count - 1
      ? count - 1
      : defaultActiveIndex;
  });

  const next = () => setActiveIndex((activeIndex + 1) % count);
  const prev = () => setActiveIndex(activeIndex - 1 < 0 ? count - 1 : activeIndex - 1);

  return {
      goto: setActiveIndex,
      next,
      prev,
      current: activeIndex
  }

};

export default useCarousel;
