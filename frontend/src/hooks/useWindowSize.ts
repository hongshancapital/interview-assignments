import { useLayoutEffect, useState } from 'react';
import { throttle } from '../utils/throttle';

export function useWindowSize() {
  const [size, setSize] = useState([0, 0]);
  useLayoutEffect(() => {
    function updateSize() {
      setSize([window.innerWidth, window.innerHeight]);
    }
    const throttleUpdateSize = throttle(updateSize, 200);
    window.addEventListener('resize', throttleUpdateSize);
    updateSize();
    return () => window.removeEventListener('resize', throttleUpdateSize);
  }, []);
  return size;
}