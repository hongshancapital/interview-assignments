/*
 * @Author: danjp
 * @Date: 2022/6/23 16:17
 * @LastEditTime: 2022/6/23 16:17
 * @LastEditors: danjp
 * @Description:
 */

import { useEffect, useMemo, useRef, useState } from 'react';

export type SlideParams = {
  count: number;
  duration: number;
  width: number;
  initialIndex: number;
}

export type SlideGoToParams = {
  type: 'next' | 'prev';
  index?: number;
}

const useSlide = (options: SlideParams) => {
  const {
    count,
    duration,
    width,
    initialIndex,
  } = options;
  
  const carouselRef = useRef<HTMLDivElement | null>(null);
  
  // initialIndex只在初始化时有用，后续变更不重新计算和渲染
  const realInitialIndex = useMemo(() => (
    initialIndex! >= count ? count - 1 : (initialIndex! < 0 ? 0 : initialIndex!)
  ), [count]);
  
  const [currentIndex, setCurrentIndex] = useState(realInitialIndex);
  
  // 实现初始化定位
  // 如果使用 transform 实现，第一次打开页面会从第一个滑动到指定index
  // 和视频中不符，视频中是默认定位到指定index
  useEffect(() => {
    if (realInitialIndex === 0) return;
    const carouselDom = carouselRef.current;
    if (!carouselDom) return;
    
    const offset = realInitialIndex * -1 * width;
    
    carouselDom.style.marginLeft = `${offset}px`;
  }, [width]);
  
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
    }
    
    const offset = nextIndex * -1 * width;
    carouselDom.style.marginLeft = '0px';
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
