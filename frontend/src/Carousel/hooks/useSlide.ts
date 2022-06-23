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
}

export type SlideGoToParams = {
  type: 'next' | 'prev' | 'index';
  index?: number;
}

const useSlide = (options: SlideParams) => {
  const {
    count,
    duration,
    width,
  } = options;
  
  const carouselRef = useRef<HTMLDivElement | null>(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  
  const slideGoTo  = ({
    type = 'next',
    index,
  }: SlideGoToParams) => {
    if (count <= 1) return;
    
    const carouselDom = carouselRef.current;
    if (!carouselDom) return;
    
    let nextIndex = 0;
    if (type === 'next') {
      nextIndex = currentIndex === count - 1 ? 0 : currentIndex + 1;
    } else if (type === 'prev') {
      nextIndex = currentIndex === 0 ? count - 1 : currentIndex - 1;
    } else if (type === 'index') {
      nextIndex = index! >= count ? count - 1 : (index! < 0 ? 0 : index!);
    }
    
    const offset = nextIndex * -1 * width;
    carouselDom.style.transition = `all ${duration}ms`;
    carouselDom.style.transform = `translateX(${offset}px)`;
    
    setCurrentIndex(nextIndex);
  };
  
  const onNext = () => {
    slideGoTo({ type: 'next' });
  };
  
  const onPrev = () => {
    slideGoTo({ type: 'prev' });
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
