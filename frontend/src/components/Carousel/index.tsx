import React, { ReactElement, useEffect, useState } from 'react';
import { CarouselIndicator } from './indicator';
import { CarouselItem, ICarouselItem } from './item';
import './index.css';

/** 轮播图属性定义 */
export interface ICarousel {
  /** 轮播项 */
  items?: ICarouselItem[];
  /** 自动滚动间隔 */
  interval?: number;
  /** 是否自动滚动 */
  auto?: boolean;
  /** 是否显示指示器 */
  showIndicator?: boolean;
  children?: ReactElement[];
  /** 点击轮播项时回调 */
  onClick?: (item: ICarouselItem, index: number) => void;
}

export * from './item';
export * from './indicator';

/**
 * 轮播图
 */
export default function Carousel({
  items = [],
  interval = 5000,
  auto = false,
  onClick,
  children,
}: ICarousel) {
  const [currIndex, setCurrIndex] = useState<number>(0);
  let timer: NodeJS.Timeout;
  let count = children && children.length > 0 ? children.length : items.length;

  useEffect(() => {
    if (!auto) return;
    timer = setTimeout(() => {
      setCurrIndex((index) => {
        return index === count - 1 ? 0 : index + 1;
      });
    }, interval);
    return () => clearTimeout(timer);
  }, [currIndex, interval, count]);

  const onClickIndicator = (index: number) => {
    if (index === currIndex) return;
    clearTimeout(timer);
    setCurrIndex(index);
  };

  function getType(item: ICarouselItem = {}, index: number) {
    return (
      <CarouselItem
        key={`${item.url}_${index}`}
        url={item.url}
        {...item}
        onClick={() =>
          onClick && typeof onClick === 'function' && onClick(item, index)
        }
      ></CarouselItem>
    );
  }

  const getIndicator = (index: number) => {
    return (
      <CarouselIndicator
        key={index}
        interval={interval}
        isActive={currIndex === index}
        auto={auto}
        onClick={() => onClickIndicator(index)}
      ></CarouselIndicator>
    );
  };

  return (
    <div className='carousel_panel'>
      <div
        className='carousel_panel__items'
        style={{
          transform: `translateX(-${(currIndex * 100) / count}%)`,
          width: `${count}00%`,
        }}
      >
        {children && children.length > 0
          ? children
          : items.map((item, index) => getType(item, index))}
      </div>
      <div className='carousel_panel__indicator'>
        {Array.from(new Array(count)).map((item, index) =>
          getIndicator(index as number)
        )}
      </div>
    </div>
  );
}
