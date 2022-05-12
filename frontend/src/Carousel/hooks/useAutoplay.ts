import { useEffect, useState } from "react";
import { CarouselProps } from "..";

type useAutoPlayProps = Required<
  Pick<CarouselProps, "autoPlay" | "interval">
> & {
  count: number;
};

export function useAutoPlay({ autoPlay, interval, count }: useAutoPlayProps) {
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    if (!autoPlay) return;
    const timer = setInterval(() => {
      setActiveIndex((activeIndex + 1) % count);
    }, interval);
    return () => clearInterval(timer);
  });

  return { activeIndex, setActiveIndex };
}
