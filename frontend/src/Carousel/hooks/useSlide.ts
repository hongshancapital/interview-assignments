/*
 * @Author: danjp
 * @Date: 2022/6/23 16:17
 * @LastEditTime: 2022/6/23 16:17
 * @LastEditors: danjp
 * @Description:
 */

import { useRef, useState } from 'react';

export type SlideParams = {
  count: number;
  duration: number;
  width: number;
  initialIndex: number;
}

export type SlideGoToParams = {
  direction: 'next' | 'prev';
  
}

const useSlide = (options: SlideParams) => {
  const {
    count,
    duration,
    width,
    initialIndex,
  } = options;
  
  const carouselRef = useRef<HTMLDivElement | null>(null);
  const [currentIndex, setCurrentIndex] = useState(() => {
    if (initialIndex < 0) return 0;
    if (initialIndex >= count) return count - 1;
    return initialIndex;
  });
  
  const slideGoTo  = ({
    direction = 'next',
  }: SlideGoToParams) => {
    if (count <= 1) return;
    
    const carouselDom = carouselRef.current;
    if (!carouselDom) return;
    
    let nextIndex = 0;
    if (direction === 'next') {
      nextIndex = currentIndex === count - 1 ? 0 : currentIndex + 1;
    } else if (direction === 'prev') {
      nextIndex = currentIndex === 0 ? count - 1 : currentIndex - 1;
    }
    
    const offset = nextIndex * -1 * width;
    carouselDom.style.transition = `all ${duration}ms`;
    carouselDom.style.transform = `translateX(${offset}px)`;
    
    setCurrentIndex(nextIndex);
  };
  
  const onNext = () => {
    slideGoTo({ direction: 'next' });
  };
  
  const onPrev = () => {
    slideGoTo({ direction: 'prev' });
  };
  
  return {
    carouselRef,
    currentIndex,
    slideGoTo,
    onNext,
    onPrev,
  };
};

export default useSlide;
