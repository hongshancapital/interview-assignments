import { useCallback, useEffect, useState } from 'react';

interface useCounterOption {
  initCount?: number;
  duration?: number;
  maxCount?: number;
  pauseWhen?: () => boolean | undefined;
}

/**
 * 计数器hooks
 * @param {useCounterOption}  选项
 */
export const useCounter = ({ initCount = 0, duration = 1000, maxCount = 10, pauseWhen }: useCounterOption) => {
  const [current, setCurrent] = useState(initCount);

  const increase = useCallback(() => setCurrent(Math.abs((current + 1) % maxCount)), [current, maxCount]);
  const decrease = useCallback(() => setCurrent(Math.abs((current - 1) % maxCount)), [current, maxCount]);

  useEffect(() => {
    const timer = setInterval(() => {
      if (pauseWhen && pauseWhen() === true) return;
      increase();
    }, duration);
    return () => {
      clearInterval(timer);
    };
  }, [duration, increase, pauseWhen]);

  return { current, increase, decrease };
};
