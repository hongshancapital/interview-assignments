import React, { useEffect, useRef, useState } from "react";

/*
 * 提供基于时间重复调用callback的hooks(通用hook)
 * @params callback: setInterval执行的回调函数
 * @params delay: setInterval的间隔时间
 */
type CallbackType = () => void;
export function useInterval(callback: CallbackType, delay: number): void {
  useEffect(() => {
    const timer = setInterval(() => {
      callback();
    }, delay);
    return () => clearInterval(timer);
  }, [callback, delay]);
}

/*
 * 提供实现slider轮播逻辑的底层(业务hook)
 * @params N: 轮播元素个数
 * @params duration: 播放的时间间隔
 * *****************************
 * @return slider: 轮播的位置
 * @return containerRef: 外层的容器
 * @return containerWidth: 外层容器的宽度
 * @return handleDotClick: 点击下面的dot可以跳转到指定的轮播
 */
type ReturnCarouselType = {
  slider: number;
  containerRef: React.RefObject<HTMLDivElement>;
  containerWidth: number;
  handleDotClick: (index: number) => void;
};
export function useCarousel(N: number, duration: number): ReturnCarouselType {
  // 容器相关的
  const containerRef = useRef<HTMLDivElement>(null);
  const [containerWidth, setConatainerWidth] = useState<number>(0);
  // slider滑块的位置
  const [slider, setSlider] = useState<number>(0);
  useInterval(() => {
    setSlider((currentSlider) =>
      currentSlider === N - 1 ? 0 : currentSlider + 1
    );
  }, duration);

  // 点击轮播下方dot
  const handleDotClick = (index: number) => {
    setSlider(index);
  };

  useEffect(() => {
    if (containerRef.current) {
      setConatainerWidth(containerRef.current.clientWidth);
    }
  }, [containerRef]);

  return {
    slider,
    containerRef,
    containerWidth,
    handleDotClick,
  };
}
