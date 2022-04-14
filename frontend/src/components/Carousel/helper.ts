import { useState, useRef, useEffect } from "react";

const TIMER = 3000;

export const useActiveIndex = (count: number) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const timerId = useRef<ReturnType<typeof setTimeout> | number>(
    0
  );
  useEffect(() => {
    timerId.current = setTimeout(()=>{
        setActiveIndex(prv => (prv +1) % count );
    },TIMER)
    return () => {
        timerId.current && clearTimeout(timerId.current as number)
    };
  }, [activeIndex, count]);

  return activeIndex;
};
