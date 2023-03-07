import { useState, useEffect } from 'react';

interface useActiveIndexProps {
  duration?: number;
  count: number;
}

function useActiveIndex(props: useActiveIndexProps) {
  const { duration = 3000, count } = props;
  const [curIndex, setCurIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      const index = (curIndex + 1) % count;
      setCurIndex(index);
    }, duration);
    return () => clearInterval(timer);
  }, [curIndex, duration, count]);

  return { curIndex, setCurIndex };
}

export default useActiveIndex;
