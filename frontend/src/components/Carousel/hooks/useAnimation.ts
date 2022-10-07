import { useEffect, useRef } from 'react';

function useAnimation(callback: Function, delay: number) {

  const intervarRef = useRef<NodeJS.Timer>();
  const callbackRef = useRef<Function>(() => {});

  const startInterval = () => {
    intervarRef.current = setInterval(() => {
      callbackRef.current();
    }, delay)
  }

  useEffect(() => {
    callbackRef.current = callback;
  }, [callback])

  useEffect(() => {
    startInterval();
    return () => intervarRef.current && clearInterval(intervarRef.current);
  })

  // 重置定时器并重新启动
  const resetInterval = () => {
    intervarRef.current && clearInterval(intervarRef.current);
    startInterval();
  }

  return resetInterval;
}

export default useAnimation;