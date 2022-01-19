import { useRef, useEffect } from 'react';

type HanlderType = Parameters<typeof setInterval>[0];
type TimerIDType = ReturnType<typeof setInterval>;
export default function useInterval(handler: HanlderType, delay: number): Array<() => void> {
  // @ts-ignore
  const timerRef = useRef<TimerIDType>(0);

  useEffect(() => {
    timerRef.current = setInterval(handler, delay);
    return () => {
      clearInterval(timerRef.current);
    };
  }, [handler, delay]);

  const stopInterval = () => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }
  }

  const resetInterval = () => {
    stopInterval();
    timerRef.current = setInterval(handler, delay);
  }

  return [resetInterval, stopInterval];
}