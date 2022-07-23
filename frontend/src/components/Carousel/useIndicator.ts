import { useEffect, useState } from "react";

export const useIndicator = ({
  len,
  duration,
}: {
  len: number;
  duration: number;
}): {
  indicator: number;
} => {
  const [indicator, setIndicator] = useState<number>(0);

  useEffect(() => {
    let timer: NodeJS.Timer;

    timer = setTimeout(() => {
      if (indicator >= len - 1) {
        setIndicator(0);
      } else {
        setIndicator(indicator + 1);
      }
    }, duration);

    return () => {
      clearTimeout(timer);
    };
  }, [len, duration, indicator]);

  return {
    indicator,
  };
};
