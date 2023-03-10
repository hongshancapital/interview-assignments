import { useState, useEffect } from 'react';

interface useActiveIndexProps {
  duration?: number;
  count: number;
}

function useActiveIndex(props: useActiveIndexProps) {
  const { duration = 3000, count } = props;
  const [nextIndex, setNextIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      const index = (nextIndex + 1) % count;
      setNextIndex(index);
    }, duration);
    return () => clearInterval(timer);
  }, [nextIndex, duration, count]);

  return { nextIndex, setNextIndex };
}

export default useActiveIndex;
