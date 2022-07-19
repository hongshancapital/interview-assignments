import { useState, useEffect } from 'react';

interface useActiveIndexProps {
  defaultIndex?: number;
  duration?: number;
  count: number;
}

function useActiveIndex(props: useActiveIndexProps) {
  const { defaultIndex = 0, duration = 3000, count } = props;
  const [curIndex, setCurIndex] = useState(defaultIndex);

  useEffect(() => {
    const timer = setInterval(() => {
      const index = (curIndex + 1) % count;
      setCurIndex(index);
    }, duration);
    return () => clearInterval(timer);
  }, [curIndex, duration, count]);
  
  return curIndex;
}

export default useActiveIndex;
