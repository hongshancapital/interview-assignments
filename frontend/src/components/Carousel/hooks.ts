import { useState, useEffect } from 'react';

interface Props {
  defaultIndex?: number,
  time?: number;
  count: number;
}

export function useActiveIndex(props: Props) {

  const { defaultIndex = 0, time = 3000, count } = props;
  const [index, setIndex] = useState(defaultIndex);

  useEffect(() => {

    const timer = setInterval(() => {
      const i = (index + 1) % count;
      setIndex(i);
    }, time);

    return () => {
      clearInterval(timer);
    }

  }, [index, count, time]);

  return index;
} 
