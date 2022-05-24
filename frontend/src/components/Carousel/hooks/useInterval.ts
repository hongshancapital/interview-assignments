import { useEffect, useRef } from "react";

export const useInterval = (
  callback: () => void,
  delay: number | null,
  exec: boolean
) => {
  const localCallback = useRef(callback);
  const timeId = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    localCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    if (!exec) {
      return;
    }
    if (typeof delay !== "number" || delay < 0 || isNaN(delay)) {
      return;
    }

    const loop = () => {
      localCallback.current?.();
      timeId.current = setTimeout(loop, delay);
    };

    timeId.current = setTimeout(loop, delay);

    return () => {
      if (timeId.current) {
        clearTimeout(timeId.current);
      }
    };
  }, [delay, exec]);
};
