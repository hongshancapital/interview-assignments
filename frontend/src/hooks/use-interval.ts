import { useEffect, useRef } from 'react';

type Noop = (...args: any[]) => any;

export const useInterval = (callback: Noop, delay?: number | null) => {
  const savedCallback = useRef<Noop>(() => {});

  useEffect(() => {
    savedCallback.current = callback;
  });

  useEffect(() => {
    if (delay !== null) {
      const interval = setInterval(() => savedCallback.current(), delay || 0);
      return () => clearInterval(interval);
    }

    return undefined;
  }, [delay]);
};
