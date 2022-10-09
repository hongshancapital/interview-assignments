import { useRef, useEffect, useCallback } from 'react';

export function useInterval(callback: Function, delay: null | number) {
  const refIntervalId = useRef<NodeJS.Timer | null>(null);
  const refSavedCallback = useRef<Function>(()=>{});

  // 
  const setUpInterval = useCallback(() => {
    if (delay !== null) {
      refIntervalId.current = setInterval(() => {
        refSavedCallback.current();
      }, delay);
    }
  }, [delay])

  // 清除定时器
  const cleanUpInterval = () => {
    refIntervalId.current && clearInterval(refIntervalId.current);
  };
  // 重置定时器
  const resetInterval = () => {
    cleanUpInterval();
    setUpInterval();
  };

  // 每次回调函数引用变化时，缓存
  useEffect(() => {
    refSavedCallback.current = callback;
  }, [callback]);

  // 安装定时器
  useEffect(() => {
    setUpInterval();
    return cleanUpInterval;
  }, [delay, setUpInterval]);

  return resetInterval;
}
