/*
 * @Author: danjp
 * @Date: 2022/6/23 16:13
 * @LastEditTime: 2022/6/23 16:13
 * @LastEditors: danjp
 * @Description:
 */
import React, { useImperativeHandle, useMemo } from 'react';
import useSlide from './hooks/useSlide';
import styles from './Carousel.module.scss';
import useRect from './hooks/useRect';
import CarouselDots from './CarouselDots';
import useResize from './hooks/useResize';
import classNames from 'classnames';

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

export interface CarouselRef {
  current: number,
  goTo: (index: number) => void;
  next: () => void;
  prev: () => void;
}

const Carousel = React.forwardRef<CarouselRef, CarouselProps>((props, ref) => {
  const {
    autoplay = 3000,
    duration = 500,
    initialIndex = 0,
    children,
    className,
    style,
  } = props;
  
  const count = useMemo(() => React.Children.count(children), [children]);
  
  const {
    rootRef,
    itemWidth,
    onChangeSize,
  } = useRect([count]);
  
  const {
    current,
    slideGoTo,
  } = useSlide({
    count,
    initialIndex,
  });
  
  useImperativeHandle(ref, () => ({
    current,
    prev: () => slideGoTo(-1),
    next: () => slideGoTo(1),
    goTo: slideGoTo,
  }), [current]);
  
  // 视图大小发生变化，重新获取容器宽高
  useResize(onChangeSize);
  
  const handleTransitionEnd = () => {
    slideGoTo(1);
  }
  
  const carouselStyle = useMemo(() => ({
    width: itemWidth * count,
    transition: `all ${duration}ms`,
    transform: `translateX(${current * itemWidth * -1}px)`
  }), [current, itemWidth, duration]);
  
  return (
    <div
      ref={rootRef}
      className={classNames(styles.carousel, className)}
      style={style}
    >
      <div
        className={styles.carousel__container}
        style={carouselStyle}
      >
        {children}
      </div>
      {/* 指示符宽度动画过渡完成再轮播下一页 */}
      <CarouselDots current={current} count={count} duration={autoplay} onTransitionEnd={handleTransitionEnd} />
    </div>
  );
});

export default Carousel;
