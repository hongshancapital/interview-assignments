import { useEffect, useRef } from "react";

export const useInterval = (fn: () => void, delay?: number) => {
  const fnRef = useRef(fn);
  fnRef.current = fn;
  useEffect(() => {
    if (typeof delay !== "number" || Number.isNaN(delay) || delay < 0) {
      return;
    }
    let timer = setInterval(fnRef.current, delay);
    return () => clearInterval(timer);
  }, [delay]);
};
