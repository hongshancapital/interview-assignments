import React, { FC, useState, SetStateAction, CSSProperties, useEffect, useRef } from "react";
import classNames from "classnames";
import useInterval from './useinterval';

import { ICarouselProps } from "./type";

import CarouselItem from "./item";
import CarouselButton from "./button";

import './index.css';

/**
 * how to use
 * <Carousel items={items} />
 */

/**
 * 内部结构
 *  <Carousel>
 *    <div class="carousel-items">
 *      <CarouselItem />
 *      <CarouselItem />
 *      <CarouselItem />
 *    </div>
 *    <div class="carousel-buttons">
 *      <CarouselButton />
 *      <CarouselButton />
 *      <CarouselButton />
 *    </div>
 *  </Carousel>
 */

/**
 * Carousel
 * @param items 数据
 * @param current 初始播放 index
 * @param duration 每一页播放时间
 * @param transitionDuration 页间切换效果过渡时间
 * @param style 透传
 * @param className 透传
 */
const Carousel: FC<ICarouselProps> = ({ items, current = 0, duration = 3000, transitionDuration = 600, style, className }) => {
  const itemsCount = items.length;
  if (current < 0) {
    current = 0;
  } else if (current >= itemsCount) {
    current = itemsCount - 1;
  }
  const [playIndex, setPlayIndex] = useState<number>(current);
  const [itemWidth, setItemWidth] = useState<number>(0)

  // 切换页面
  const setNext = (index: SetStateAction<number>) => {
    setPlayIndex(index);
    resetInterval();
  };

  // 自动循环播放
  const [resetInterval] = useInterval(() => {
    setNext((prevIndex) => {
      return prevIndex + 1 >= itemsCount ? 0 : prevIndex + 1;
    });
  }, duration);

  // 计算宽度（组件自适应容器的宽度和高度）
  const elRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (elRef.current) {
      setItemWidth(elRef.current.clientWidth);
    }
  }, []);

  // 切换效果使用 transtion + transform(translateX)
  // 页面按顺序排列在同一行， 则第 n 页的 translateX 为：
  // f(n) = -Percent(n / count)
  const itemsContainerStyle: CSSProperties = {
    transform: `translateX(${-playIndex / itemsCount * 100}%)`,
    transitionDuration: `${transitionDuration / 1000}s`,
    ...style
  };

  return (
    <div className="carousel" ref={elRef}>
      <div className={classNames("carousel-items", className)} style={itemsContainerStyle}>
        {
          items.map((item, index) => (
            <CarouselItem
              item={item}
              width={itemWidth}
              isActive={playIndex === index}
              key={`carousel-item-${index}`}
            />
          ))
        }
      </div>
      <div className="carousel-buttons">
        {
          items.map((item, index) => (
            <CarouselButton
              key={`carousel-button-${index}`}
              isActive={playIndex === index}
              onClick={() => { setNext(index) }}
              duration={duration}
            />
          ))
        }
      </div>
    </div>
  );
};

export default Carousel;