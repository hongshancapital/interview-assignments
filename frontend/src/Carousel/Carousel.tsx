/*
 * @Author: danjp
 * @Date: 2022/6/23 16:13
 * @LastEditTime: 2022/6/23 16:13
 * @LastEditors: danjp
 * @Description:
 */
import React, { useEffect, useMemo, useRef, useState } from 'react';
import useSlide from './hooks/useSlide';
import styles from './Carousel.module.scss';
import useRect from './hooks/useRect';

export interface CarouselProps {
  // 自动轮播时长，单位毫秒
  autoplay?: false | number;
  // 轮播过渡时长，单位毫秒
  duration?: number;
  // 初始化定位序号
  initialIndex?: number;
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
}

const Carousel: React.FC<CarouselProps> = (props) => {
  const {
    autoplay = 3000,
    duration = 1000,
    initialIndex = 0,
    children,
    className,
    style,
  } = props;
  
  const count = useMemo(() => React.Children.count(children), [children]);
  
  const {
    rootRef,
    itemWidth,
  } = useRect([count]);
  
  // 所有轮播组件总宽度
  const containerStyle = useMemo(() => ({
    width: itemWidth * count
  }), [itemWidth, count]);
  
  const {
    carouselRef,
    currentIndex,
    slideGoTo,
    onNext,
    onPrev,
  } = useSlide({
    count,
    duration,
    width: itemWidth,
    initialIndex,
  });
  
  /*useEffect(() => {
    if (itemWidth) {
      slideGoTo({
        type: 'index',
        index: initialIndex,
      })
    }
  }, [initialIndex, itemWidth]);*/
  
  const autoPlayTimer = useRef<NodeJS.Timeout | null>(null);
  // 自动轮播
  const onAutoPlay = () => {
    if (count <= 1) return;
    if (!autoplay) return;
    
    autoPlayTimer.current = setTimeout(() => {
      onNext();
    }, autoplay);
  };
  
  const onStopPlay = () => {
    autoPlayTimer.current && clearTimeout(autoPlayTimer.current)
    autoPlayTimer.current = null;
  };
  
  useEffect(() => {
    onAutoPlay();
    return onStopPlay;
  }, [count, autoplay, currentIndex, itemWidth]);
  
  return (
    <div
      ref={rootRef}
      className={styles.carousel}
      style={style}
    >
      <div
        ref={carouselRef}
        className={styles.carousel__container}
        style={containerStyle}
      >
        {React.Children.map(children, (child, index) => {
          if (!React.isValidElement(child)) return null;
          return (
            React.cloneElement(child, {
              style: { width: itemWidth },
            })
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
