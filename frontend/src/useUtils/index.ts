import { useEffect } from 'react';

export function useInterval(cb: () => void, interval: number) {
  useEffect(() => {
    const intervalId = setInterval(cb, interval);
    return () => clearInterval(intervalId);
  }, [cb, interval]);
}
