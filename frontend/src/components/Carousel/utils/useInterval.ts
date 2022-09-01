import { useRef, useEffect } from 'react';

export function useInterval(callback: Function, delay: null | number) {
  const refIntervalId = useRef<any>(null);
  const refSavedCallback = useRef<Function>(()=>{});

  // 
  const setUpInterval = () => {
    if (delay !== null) {
      refIntervalId.current = setInterval(() => {
        refSavedCallback.current();
      }, delay);
    }
  };

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
  }, [delay]);

  return resetInterval;
}
