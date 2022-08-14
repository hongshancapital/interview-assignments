import React, { FC, memo, PropsWithChildren, ReactElement, useEffect, useImperativeHandle, useMemo, useState } from 'react';
import { CarouselItem } from './CarouselItem';
import styles from './Carousel.module.css';
import { Indicator } from './Indicator';

export interface CarouselProps {
  /** 当前页面持续时间，单位:ms，默认:6000 */
  duration?: number;
  indicator?: boolean;
  indicatorWidth?: number;
  onChange?: (current: number) => void;
}

const MemoCarousel: FC<CarouselProps> = memo<PropsWithChildren<CarouselProps>>(
  ({ duration = 4000, indicator = true, indicatorWidth = 40, children }) => {
    // 当前所显示子元素的索引
    const [current, setCurrent] = useState(0);

    const childrenArray = React.Children.toArray(children);

    // 获取跑马灯子元素
    const items = childrenArray
      .filter((c: any) => c.type === CarouselItem)
      .map((c: any, i) => React.cloneElement(c, { style: { transform: `translate3d(${i * 100}%, 0, 0)` } }));

    // 用于处理当前跑马灯动画
    const innerStyle = useMemo(() => {
      return { transform: `translate3d(${-current * 100}%, 0, 0)` };
    }, [current]);

    useEffect(() => {
      const timer = setInterval(() => {
        setCurrent((prev) => {
          if (prev >= items.length - 1) {
            return 0;
          }
          return prev + 1;
        });
      }, duration);
      return () => clearInterval(timer);
    }, [duration, items.length]);

    return (
      <div className={styles.carousel}>
        <div className={styles.carouselInner} style={innerStyle}>
          {items}
        </div>
        {indicator && (
          <div className={styles.indicatorOuter} key={items.length}>
            {items.map((_, i) => (
              <Indicator duration={duration} key={i} active={i === current} width={indicatorWidth} />
            ))}
          </div>
        )}
      </div>
    );
  }
);

export const Carousel = MemoCarousel as FC<CarouselProps> & { Item: typeof CarouselItem };

Carousel.Item = CarouselItem;
Carousel.displayName = 'Carousel';
