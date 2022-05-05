import React, { FC, useCallback } from "react";
import { CarouselProps, SlideDirection, ArrowKeys } from '../../types/carousel';
import { useSlider, useInterval } from '../../hooks';
import CarouselItems from '../Items/Items';
import CarouselDots from "../Dots/Dots";
import './index.css';

// 轮播图默认配置
export const defaultProps: Required<CarouselProps> = {
  children: [],
  className: '', 
  duration: 1000,
  delay: 3000,
  autoPlay: true,
  useArrowKeys: true
};
/**
 * Carousel 轮播图组件
 * <Carousel>
 *    <children>
 * </Carousel> 
 * @param props.className 组件类名
 * @param props.duration 轮播过渡时间
 * @param props.delay 轮播时间
 * @param props.autoPlay 自动轮播
 * @param props.useArrowKeys 使用键盘控制轮播
 */
const Carousel: FC<CarouselProps> = (props) => {
    const { children, className, duration, delay, autoPlay, useArrowKeys } = { ...defaultProps, ...props }; 
    const count = children.length;
    // 轮播图控件
    const [ currentStateIndex, { slideToDirection, slideToIndex } ] = useSlider(count);
    // 自动轮播
    const resetInterval = useInterval(slideToDirection, delay, autoPlay && count > 1);

    // 指定位置进行滑动
    const onSlideToIndex = useCallback((index: number) => {
      slideToIndex(index);
      resetInterval();
    }, []); // eslint-disable-line react-hooks/exhaustive-deps
  
    // 指定方向进行滑动
    const onSlideToDirection = (direction: SlideDirection) => {
        slideToDirection(direction);
        resetInterval();
    }
  
    // 监听键盘事件
    const handleOnKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
      if (!useArrowKeys) return;
      if (e.keyCode === ArrowKeys.Left) {
        onSlideToDirection(SlideDirection.Left);
      } else if (e.keyCode === ArrowKeys.Right) {
        onSlideToDirection(SlideDirection.Right);
      }
    }

    return <div
      data-testid="carousel"
      className={`carousel ${className}`}
      tabIndex={-1}
      onKeyDown={handleOnKeyDown}
    >
      <CarouselItems
        duration={duration}
        currentStateIndex={currentStateIndex}>
        {children}
      </CarouselItems>

      <CarouselDots
        count={count}
        currentStateIndex={currentStateIndex}
        onSlideToIndex={onSlideToIndex}
        delay={delay}
      />
    </div>
}

export default Carousel;