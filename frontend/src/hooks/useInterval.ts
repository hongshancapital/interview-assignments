import { useEffect, useRef } from 'react'

type Tfun = (fn: () => void, delay: number) => void

const useInterval: Tfun = (fn, delay) => {
  const fnRef = useRef<Function>();
  fnRef.current = fn;
  useEffect(() => {
    if (typeof delay !== 'number' || delay <= 0) return;
    const timer = setInterval(() => {
      fnRef.current && fnRef.current();
    }, delay);
    return () => {
      clearInterval(timer);
    };
  }, [delay]);
}

export default useInterval;