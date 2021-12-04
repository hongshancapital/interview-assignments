import { useState, useEffect } from "react";

export const useProgressCount = (time: number, isCurrent: boolean) => {
  const [count, setCount] = useState<number>(0);

  useEffect(() => {
    if (isCurrent) {
      const raf = { preTime: 0, id: 0 };

      const animation = (startTime: number) => {
        raf.preTime = raf.preTime || startTime;
        const progress = startTime - raf.preTime;
        setCount(progress);

        if (progress < time) {
          raf.id = requestAnimationFrame(animation);
        } 
      }
      raf.id = requestAnimationFrame(animation);

      return () => cancelAnimationFrame(raf.id);
    }
  }, [time, isCurrent]);

  return count / time;
}