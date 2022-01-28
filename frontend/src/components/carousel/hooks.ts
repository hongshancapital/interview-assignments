import { useRef, useEffect } from 'react';
import { TTimeID } from './type';

export const useTimer = (cb: Function, duration: number, autoPlay: boolean) => {
  let timeIdRef = useRef<TTimeID>(null);

  const clear = () => {
    timeIdRef.current && clearInterval(timeIdRef.current);
  }

  useEffect(() => {
    if (!autoPlay) return;
    if (timeIdRef.current) {
      clearInterval(timeIdRef.current);
    }
    // @ts-ignore
    timeIdRef.current = setInterval(cb, duration);
    return () => {
      clear();
    }
  }, [autoPlay, cb, duration]);


  const restart = () => {
    if (autoPlay) {
      clear();
      // @ts-ignore
      timeIdRef.current = setInterval(cb, duration);
    }
  }
  return [restart];
};