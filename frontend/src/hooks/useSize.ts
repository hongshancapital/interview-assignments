import { RefObject, useState, useLayoutEffect, useRef } from 'react';
import ResizeObserver from 'resize-observer-polyfill';

export type Size = {
  width: number;
  height: number;
}

export const useSize = <T extends HTMLElement>(): {
  ref: RefObject<T>;
  size: Size;
} => {
  const [size, setSize] = useState<Size>({ width: 0, height: 0 });
  const ref = useRef<T>(null);

  useLayoutEffect(() => {
    const observer = new ResizeObserver((entries) => {
      if (!entries?.length) {
        return;
      }
      const { width, height } = entries[0].contentRect || {};
      setSize({ width, height });
    });

    if (ref.current) {
      observer.observe(ref.current);
    }

    return () => {
      observer.disconnect();
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [ref.current]);

  return {
    ref,
    size,
  }
}