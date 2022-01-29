import {DependencyList, useEffect, useRef, useState} from 'react';

export function useRect<T extends HTMLElement>(deps: DependencyList) {
  const [size, setSize] = useState<{ width: number; height: number }>({ width: 0, height: 0 });
  const wrapperRef = useRef<T | null>(null);

  const setCurrentRect = () => {
    const rect = wrapperRef.current?.getBoundingClientRect();
    if (rect) {
      setSize({ width: rect.width, height: rect.height });
    }
  }

  useEffect(() => {
    setCurrentRect();
    // eslint-disable-next-line
  }, deps)

  return {
    size,
    wrapperRef,
    setCurrentRect,
  }
}
