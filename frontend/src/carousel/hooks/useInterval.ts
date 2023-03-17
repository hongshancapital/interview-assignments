import { useEffect, useRef } from "react";

export function useInterval(
  callback: Function,
  delay: number | null = null,
  forceRefreshToken: number = 0
) {
  const savedCallback = useRef<Function>();

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    const tick = () => {
      if (savedCallback.current !== undefined) {
        savedCallback.current();
      }
    };

    if (delay !== null) {
      const id = setInterval(tick, delay);
      return () => {
        return clearInterval(id);
      };
    }
  }, [delay, forceRefreshToken]);
}
