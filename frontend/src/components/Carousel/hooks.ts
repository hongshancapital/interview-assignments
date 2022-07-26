import { useEffect, useRef, useState } from "react";
import { CarouselItem } from "./types";

export const useSlide = (
  items: CarouselItem[],
  interval: number,
  animationDuration: number,
  onChange?: (item: CarouselItem, index: number) => void
): [number, boolean] => {
  const [index, setIndex] = useState(0);
  const [isFirstRender, seIsFirstRender] = useState(true);
  const onChangeRef = useRef(onChange);
  const timer = useRef<NodeJS.Timeout | null>();
  const nextIndex = (index + 1) % items.length;

  useEffect(() => {
    seIsFirstRender(false);
    timer.current = setTimeout(() => {
      setIndex(nextIndex);
      onChangeRef.current?.(items[nextIndex], nextIndex);
    }, interval + animationDuration);

    return () => {
      if (timer.current) {
        clearTimeout(timer.current);
        timer.current = null;
      }
    };
  }, [items, interval, animationDuration, nextIndex]);

  return [index, isFirstRender];
};
