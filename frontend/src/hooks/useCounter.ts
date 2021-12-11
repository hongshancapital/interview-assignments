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
  const [currentValue, setCurrent] = useState(initCount);

  const increase = useCallback(() => setCurrent((currentValue + 1) % maxCount), [currentValue, maxCount]);
  const decrease = useCallback(() => setCurrent(currentValue - 1 < 0 ? maxCount - 1 : currentValue - 1), [currentValue, maxCount]);

  useEffect(() => {
    const timer = setInterval(() => {
      if (pauseWhen && pauseWhen() === true) return;
      increase();
    }, duration);
    return () => {
      clearInterval(timer);
    };
  }, [duration, increase, pauseWhen]);

  const action = { increase, decrease, setCurrent }
  return [currentValue, action] as [currentValue: typeof currentValue, action: typeof action];
};
