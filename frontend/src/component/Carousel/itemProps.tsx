import { useEffect, useState } from 'react';
import { DataProps } from './index';

export function useItemIndex(props: carouselIndexProps) {
  let [index, setIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setIndex(++index%props.count)
    }, props.time)
    return () => clearInterval(timer);
  }, [index,props.count, props.time])
  return {
    currentIndex: index,
    setCurrentIndex:setIndex
  }
}

export interface carouselIndexProps {
  count: number;
  time: DataProps['time'];
} 