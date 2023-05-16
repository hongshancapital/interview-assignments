
import { useRef, useCallback, useEffect, useLayoutEffect, type RefObject } from 'react';

export const useAutoPlay = (cb: () => any, interval: number) => {
  const timer = useRef<number>();
  const callback = useRef(cb);

  // update callback ref when cb changes
  useEffect(() => {
    callback.current = cb;
  }, [cb]);

  const start = useCallback(() => {
    timer.current = window.setInterval(() => {
      callback.current();
    }, interval);
  }, [interval, timer]);

  const stop = useCallback(() => {
    window.clearInterval(timer.current);
  }, [timer]);

  // clear timer when component is unmounted
  useEffect(() => {
    return () => window.clearInterval(timer.current);
  }, []);

  return {
    start,
    stop
  };
};

export const useDebounce = (cb: () => any, delay: number = 300) => {
  const timer = useRef<number>();
  const callback = useRef(cb);

  // update callback ref when cb changes
  useEffect(() => {
    callback.current = cb;
  }, [cb]);

  // clear timer when component is unmounted
  useEffect(() => {
    return () => window.clearTimeout(timer.current);
  }, []);

  const debounceFn = useCallback(() => {
    window.clearTimeout(timer.current);
    timer.current = window.setTimeout(() => {
      callback.current();
    }, delay);
  }, [delay, timer]);

  return debounceFn;
};


interface ResizeObserverOptions<T extends HTMLElement = HTMLElement>{
  ref: RefObject<T>;
  onResize: () => any;
  debounceDelay?: number;
}
export const useResizeObserver = (options: ResizeObserverOptions) => {
  const { ref, onResize, debounceDelay = 300 } = options;
  const handleResize = useDebounce(onResize, debounceDelay);

  useLayoutEffect(() => {
    const target = ref.current;
    if (!target) return;

    const resizeObserver = new ResizeObserver((entries) => {
      handleResize();
    });

    resizeObserver.observe(ref.current);

    return () => resizeObserver.disconnect();
  }, [ref, handleResize]);
};