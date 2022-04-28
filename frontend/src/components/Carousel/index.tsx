import React, { FC, useCallback, useRef } from "react";
import { CarouselProps, SlideDirection, ArrowKeys } from '../../types/carousel';
import { useSlider, useInterval } from '../../hooks';
import CarouselItems from '../Items/Items';
import CarouselDots from "../Dots/Dots";
import CarouselArrow from "../Arrow/Arrow";
import './index.css';

// 轮播图默认配置
export const defaultProps: Required<CarouselProps> = {
  children: [],
  className: '', 
  duration: 1000,
  delay: 3000,
  stopOnHover: false,
  autoPlay: true,
  showArrow: false,
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
 * @param props.stopOnHover 鼠标悬停停止轮播
 * @param props.autoPlay 自动轮播
 * @param props.showArrow 显示左右切换按钮
 * @param props.useArrowKeys 使用键盘控制轮播
 */
const Carousel: FC<CarouselProps> = (props) => {
    const {
        children, className, duration, delay,
        stopOnHover, autoPlay, showArrow, useArrowKeys } = { ...defaultProps, ...props }; 
    const count = children.length;
    // 轮播容器
    const carouselRef = useRef<HTMLUListElement>(null);
    // 轮播图控件
    const [ currentStateIndex, { slideToDirection, slideToIndex } ] = useSlider(carouselRef, { count, duration });
    // 自动轮播
    const { toggleIntervalSwitch, resetInterval } = useInterval(slideToDirection, delay, autoPlay && count > 1);

    // 指定位置进行滑动
    const onSlideToIndex = useCallback((index: number) => {
      slideToIndex(index + 1);
      resetInterval();
    }, []);
  
    // 指定方向进行滑动
    const onSlideToDirection = useCallback((direction: SlideDirection) => {
        slideToDirection(direction);
        resetInterval();
    }, []);

    // 监听鼠标移入/移出 
    const handleOnMouseEvent = (state: boolean) => {
      if (stopOnHover) {
        toggleIntervalSwitch(state)
      }
    };
  
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
      onMouseEnter={() => handleOnMouseEvent(false)}
      onMouseLeave={() => handleOnMouseEvent(true)}
    >
      <CarouselArrow
        showArrow={showArrow}
        onSlideToDirection={onSlideToDirection}
      />

      <CarouselItems carouselRef={carouselRef}>
        {children}
      </CarouselItems>

      <CarouselDots
        count={count}
        currentStateIndex={currentStateIndex - 1}
        onSlideToIndex={onSlideToIndex}
        delay={delay}
      />
    </div>
}

export default Carousel;