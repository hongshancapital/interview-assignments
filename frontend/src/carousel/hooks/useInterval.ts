import { useCallback, useEffect, useRef } from "react";
import { isNumber } from "../utlis/isNumber";

export function useInterval(callback: Function, delay: number | null = null) {
  const savedCallback = useRef<Function>();
  const intervalRef = useRef<NodeJS.Timer | null>(null);

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    if (!isNumber(delay) || delay < 0) {
      return;
    }
    const tick = () => {
      if (savedCallback.current !== undefined) {
        savedCallback.current();
      }
    };

    if (delay !== null) {
      intervalRef.current = setInterval(tick, delay);
      const id = intervalRef.current;
      return () => {
        return clearInterval(id);
      };
    }
  }, [delay]);

  // 重置倒计时
  const reset = useCallback(() => {
    const tick = () => {
      if (savedCallback.current !== undefined) {
        savedCallback.current();
      }
    };
    if (intervalRef.current !== null && delay !== null) {
      clearInterval(intervalRef.current);

      intervalRef.current = setInterval(tick, delay);
    }
  }, [delay]);
  return [reset];
}
