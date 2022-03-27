import { useEffect, useState } from 'react';
import { ICarouselProps } from '../components/Carousel';

export function useCarouselIndex(props: ICarouselIndexProps) {
  let [index, setIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setIndex(++index % props.count)
    }, props.durationMS)
    return () => clearInterval(timer);
  }, [props.count, props.durationMS, index])
  return {
    currentIndex: index,
  }
}

export interface ICarouselIndexProps {
  count: number;
  durationMS: ICarouselProps['durationMS'];
}