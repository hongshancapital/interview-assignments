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

export interface CarouselProps {
  // 自动轮播时长，单位毫秒
  autoplay?: number;
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
  
  const rootRef = useRef<HTMLDivElement | null>(null);
  const [size, setSize] = useState({ width: 0, height: 0 });
  
  const count = useMemo(() => React.Children.count(children), [children]);
  
  const {
    carouselRef,
    currentIndex,
    slideGoTo,
    onNext,
    onPrev,
  } = useSlide({
    count,
    duration,
    width: size.width,
  });
  
  useEffect(() => {
    if (size.width) {
      slideGoTo({
        type: 'index',
        index: initialIndex,
      })
    }
  }, [initialIndex, size.width]);
  
  const handleChangeSize = () => {
    const rect = rootRef.current?.getBoundingClientRect();
    if (!rect) return;
    
    setSize({
      width: rect.width,
      height: rect.height,
    })
  };
  
  useEffect(() => handleChangeSize(), [count]);
  
  const autoPlayTimer = useRef<NodeJS.Timeout | null>(null);
  const onAutoPlay = () => {
    if (count <= 1) return;
    if (!autoplay) return;
    
    autoPlayTimer.current = setTimeout(() => {
      onNext();
    }, autoplay);
  };
  
  useEffect(onAutoPlay, [count, autoplay, currentIndex, size.width]);
  
  return (
    <div
      ref={rootRef}
      className={styles.carousel}
      style={style}
    >
      <div
        ref={carouselRef}
        className={styles.carousel__container}
        style={{
          width: count * size.width
        }}
      >
        {React.Children.map(children, (child, index) => {
          if (!React.isValidElement(child)) return null;
          return (
            React.cloneElement(child, {
              style: { width: size.width },
            })
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
