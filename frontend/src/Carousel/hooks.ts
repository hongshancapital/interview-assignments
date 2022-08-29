import { useEffect, useState } from "react";

export const useSlider = (count: number) => {
  const [index, setIndex] = useState<number>(0);
  useEffect(() => {
    const timer = setInterval(() => {
      setIndex((idx) => (idx + 1) % count);
    }, 3000);
    return () => clearInterval(timer);
  }, [count, index]);
  return index;
};
