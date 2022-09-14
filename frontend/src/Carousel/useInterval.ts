import { useRef, useEffect } from 'react';

export default function useInterval(
  callback: () => void,
  delay: number | null
) {
  const savedCallback = useRef(() => {});

  useEffect(() => {
    savedCallback.current = callback;
  });

  useEffect(() => {
    if (typeof delay === 'number') {
      const id = setInterval(() => {
        savedCallback.current();
      }, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}
