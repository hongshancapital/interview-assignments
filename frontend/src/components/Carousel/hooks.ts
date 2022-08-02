import { useEffect, useRef, useState } from "react";
import { CarouselItem } from "./types";

export const useSlide = (
  items: CarouselItem[],
  waitTime: number,
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
    }, waitTime);

    return () => {
      if (timer.current) {
        clearTimeout(timer.current);
        timer.current = null;
      }
    };
  }, [items, waitTime, nextIndex]);

  return [index, isFirstRender];
};
