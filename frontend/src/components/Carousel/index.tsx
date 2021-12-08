import React, { ReactElement, useEffect, useState } from 'react';
import { CarouselIndicator } from './indicator';
import { CarouselItem, ICarouselItem } from './item';
import './index.css';

export * from './item';
export * from './indicator';

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

/**
 * 轮播组件
 * 实现思路：通过CSS translateX实现滚动，通过定时器进行当前展示项变化
 * @description 轮播图组件可自动或手动点击触发滚动
 * @param items: ICarouseItem[] 可选，轮播数据集合
 * @param children: ReactElement[] 可选，组件子项，展示优先级比items高
 * @param interval: number 可选，滚动间隔，默认为5000ms
 * @param auto: boolean 可选，是否自动滚动，默认为false
 * @param showIndicator: boolean 可选，是否展示指示器，默认为true
 * @param onClick: Function 可选，点击轮播项时触发
 * @todo：1、后续可尝试通过控制元素展示顺序进行优化，让轮播容器始终保持为200%，同时效果会是无限滚动而不是现在直接回到第一屏
 * @todo：2、提供更多指示器位置选项，如左侧、右侧、上面等
 * @todo：3、提供更多轮播项风格选项，如卡片
 */
export default function Carousel({
  items = [],
  interval = 5000,
  auto = false,
  showIndicator = true,
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
      {showIndicator && (
        <div className='carousel_panel__indicator'>
          {Array.from(new Array(count)).map((item, index) =>
            getIndicator(index as number)
          )}
        </div>
      )}
    </div>
  );
}
