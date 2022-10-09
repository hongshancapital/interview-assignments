import { useEffect, useState } from "react";

export const useSlider = (count: number, interval: number) => {
  const [index, setIndex] = useState<number>(0);
  useEffect(() => {
    const timer = setInterval(() => {
      setIndex((idx) => (idx + 1) % count);
    }, interval);
    return () => clearInterval(timer);
  }, [count, index, interval]);
  return index;
};
