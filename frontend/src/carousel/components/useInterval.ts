import { EffectCallback, useEffect, useRef } from "react";

export function useInterval(
  callback: EffectCallback,
  delay: number|null,
  refreshDep: number,
  forceRefresh: boolean = false
) {
  const savedCallback = useRef<EffectCallback>();

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    const tick = () => {
      if (savedCallback.current !== undefined) {
        savedCallback?.current();
      }
    };

    if (delay !== null) {
      const id = setInterval(tick, delay);
      return () => {
        return clearInterval(id);
      };
    }
  }, [delay, refreshDep, forceRefresh]);
}
