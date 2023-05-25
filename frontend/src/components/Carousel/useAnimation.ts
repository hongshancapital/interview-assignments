import { useState, useEffect, useCallback } from 'react';

export default function useAnimation(childrenLength: number, duration: number) {
  const [activeIndex, setActiveIndex] = useState(0);

  const next = useCallback(() => {
    setActiveIndex(prev => (prev + 1) % childrenLength);
  }, [childrenLength]);

  const prev = useCallback(() => {
    setActiveIndex(prev => prev > 0 ? prev - 1 : childrenLength - 1);
  }, [childrenLength]);

  const goTo = useCallback((targetIndex: number) => {
    setActiveIndex(targetIndex % childrenLength);
  }, [childrenLength]);

  useEffect(() => {
    const timer = setInterval(() => {
      next();
    }, duration);
    return () => {
      clearInterval(timer);
    }
  // activeIndex 发生变化时，重置定时器
  }, [next, duration, activeIndex]);

  return {
    activeIndex,
    goTo,
    next,
    prev,
  }
}