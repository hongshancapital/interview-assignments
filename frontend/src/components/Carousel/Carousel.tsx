import React, { useState, FC, ReactNode, useEffect, CSSProperties } from 'react';
import clsx from 'clsx';

import './Carousel.scss';

export interface CarouselOption {
  /** 标题部分 */
  title?: ReactNode;
  /** 内容部分 */
  content?: ReactNode;
  className?: string;
  style?: CSSProperties;
}

export interface CarouselProps {
  /** 轮播图配置 */
  options: CarouselOption[];
  /** 切换时间，单位: ms */
  delay?: number;
  className?: string;
  style?: CSSProperties;
}

type Props = CarouselProps;

/** 计算新的下标，环形循环 */
const calcIdx = (idx: number, len: number) => (idx + len) % len;

/** 默认切换时间 */
const DEFAULT_DELAY = 2000;

/** 轮播图 */
const Carousel: FC<Props> = ({ options, delay = DEFAULT_DELAY, className, style }) => {
  const [activeIdx, setActiveIdx] = useState(0);
  const len = options.length;

  useEffect(() => {
    const id = setTimeout(() => {
      // 切换到下一张图
      setActiveIdx((idx) => calcIdx(idx + 1, len));
    }, delay);
    return () => {
      clearTimeout(id);
    };
  }, [activeIdx, len, delay]);

  return (
    <div className={clsx('carousel-wrap', className)} style={style}>
      <div
        className="carousel-list"
        style={{ transform: `translateX(calc(-100% * ${activeIdx}))` }}
      >
        {options.map((option, idx) => (
          <div
            key={idx}
            className={clsx('carousel-item', { active: activeIdx === idx })}
            style={option.style}
          >
            {option.title && <div className="carousel-item__title">{option.title}</div>}
            {option.content && <div className="carousel-item__content">{option.content}</div>}
          </div>
        ))}
      </div>
      <div className="carousel-indicator">
        {options.map((_, idx) => {
          const active = activeIdx === idx;
          return (
            <div
              key={idx}
              className={clsx('carousel-indicator__item', { active })}
              onClick={() => setActiveIdx(calcIdx(idx, len))}
            >
              {active && (
                <div
                  className="carousel-indicator__timer"
                  style={{ animationDuration: `${delay}ms` }}
                />
              )}
            </div>
          );
        })}
      </div>
      <div
        className="carousel-nav-bar__prev"
        onClick={() => setActiveIdx(calcIdx(activeIdx - 1, len))}
      >
        {'<'}
      </div>
      <div
        className="carousel-nav-bar__next"
        onClick={() => setActiveIdx(calcIdx(activeIdx + 1, len))}
      >
        {'>'}
      </div>
    </div>
  );
};

export default Carousel;
