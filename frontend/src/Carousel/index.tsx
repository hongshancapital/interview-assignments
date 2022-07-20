import * as React from 'react';
import { useState, useEffect, useRef } from 'react';

import './index.scss';
interface CommonPropsType {
  style?: React.CSSProperties;
  className?: string;
  children?: React.ReactNode | undefined;
}

export interface PropsType extends CommonPropsType {
  /**
   * 是否启用指示点
   */
  indicatorDots?: boolean;
  /**
   * 是否自动切换
   */
  autoplay?: boolean;
  /**
   * 当前索引值
   */
  current?: number;
  /**
   * 轮换时间
   */
  interval?: number;
  /**
   * 停留时间
   */
  duration?: number;
  /**
   * 是否显示下方指示条
   */
  circular?: boolean;
  /**
   * 是否显示左右切换
   */
  turnPre?: boolean;
  indicatorColor?: string;
  indicatorActiveColor?: string;
}

export function Carousel({
  children,
  className = '',
  style,
  autoplay = true,
  circular = true,
  indicatorDots = true,
  indicatorColor = 'rgba(200, 200, 200, 0.9)',
  indicatorActiveColor = 'rgba(0, 0, 0, 0.9)',
  turnPre = false,
  duration = 500,
  interval = 3000,
  current = 0,
}: PropsType) {
  const itemCount = React.Children.count(children);
  current = Math.max(0, Math.min(itemCount - 1, current));
  const [currentIndex, setCurrentIndex] = useState(current);
  const [swipeStyle, setSwipeStyle] = useState({});
  // 自动切换
  useEffect(() => {
    if (autoplay) {
      const timer = setInterval(() => {
        setIndex(currentIndex + 1);
      }, interval);

      return () => clearInterval(timer);
    }
  }, [currentIndex, autoplay, itemCount]);

  // 触发切换
  const pre = useRef({ index: currentIndex }).current;
  useEffect(() => {
    updateSwipeStyle(currentIndex, pre.index, itemCount);
    return () => {
      pre.index = currentIndex;
    };
  }, [currentIndex]);

  const itemsClone = React.Children.map(children, (x, i) => {
    return (
      <div className="hs-swiper-item">
        {x}
      </div>
    );
  }) || [];

  const setIndex = (index: number) => {
    if (index >= itemCount) {
      index = 0;
    }
    if (index < 0) {
      index = itemCount - 1;
    }
    setCurrentIndex(index);
  };

  // 设置容器样式、动画
  const updateSwipeStyle = (to: number, cur: number, length: number) => {
    // 最后向前到最开始
    if (to === 0 && cur === length - 1) {
      circular &&
        setSwipeStyle({
          transform: `translate3d(0, 0, 0)`,
        });
      setTimeout(() => {
        setSwipeStyle({
          transform: `translate3d(-100%, 0, 0)`,
          transitionDuration: `${duration}ms`,
        });
      }, 50);
      return;
    }

    // 第一个后退到最后
    if (to === length - 1 && cur === 0) {
      circular &&
        setSwipeStyle({
          transform: `translate3d(-${(to + 2) * 100}%, 0, 0)`,
        });
      setTimeout(() => {
        setSwipeStyle({
          transform: `translate3d(-${(to + 1) * 100}%, 0, 0)`,
          transitionDuration: `${duration}ms`,
        });
      }, 100);
      return;
    }

    const style: React.CSSProperties = {
      transform: `translate3d(-${(to + 1) * 100}%, 0, 0)`,
    };
    if (to !== cur) {
      style.transitionDuration = `${duration}ms`;
    }
    setSwipeStyle(style);
  };

  return (
    <div
      className={`hs-swiper ${className}`}
      style={style}
    >
      {itemCount > 0 ? (
        <div
          className="hs-swiper-list"
          style={swipeStyle}
        >
          {itemsClone[itemCount - 1]}
          {React.Children.map(children, (x, i) => {
            return (
              <SwiperItem
                currentIndex={currentIndex}
                index={i}
              >
                {x}
              </SwiperItem>
            );
          })}
          {itemsClone[0]}
        </div>
      ) : null}

      {indicatorDots ? (
        <div className="hs-swiper__pagination">
          {React.Children.map(children, (x, i) => {
            return (
              <a
                href="#"
                className="hs-swiper__pagination-bulletm"
                style={{
                  backgroundColor:
                    currentIndex === i ? indicatorActiveColor : indicatorColor,
                }}
                onClick={(e) => {
                  setIndex(i);
                  e.preventDefault();
                }}
              ></a>
            );
          })}
        </div>
      ) : (
        false
      )}
      {
        turnPre && <>
      <a
        href="#"
        className="hs-swiper__turn-pre"
        onClick={(ev) => {
          ev.preventDefault();
          setIndex(currentIndex - 1);
        }}
      >
        上一页
      </a>
      <a
        href="#"
        className="hs-swiper__turn-next"
        onClick={(ev) => {
          ev.preventDefault();
          setIndex(currentIndex + 1);
        }}
      >
        下一页
      </a></>
      }
    </div>
  );
}

const SwiperItem: React.FC<{
  currentIndex: number;
  index: number;
  children?: React.ReactNode;
}> = ({  children, currentIndex, index }) => {
  return (
    <div
      className={`hs-swiper-item ${index === currentIndex ? 'current' : ''}`}
    >
      <div>
        {children}
      </div>
    </div>
  );
};
