import React, { CSSProperties, useEffect, useMemo, useRef, useState } from 'react';
import { mergeClassName, setStyle } from './../../utils';

import './index.css';

interface ISwiper {
  className?: string;
  autoplay?: boolean; // 是否自动播放
  autoplayInterval?: number; // 自动播放间隔ms
  children?: React.ReactNode[];
  transitionDuration?: number; // 动画过渡时长ms
}
/**
 * 轮播组件
 * @param props
 * @returns
 */
const Swiper: React.FC<ISwiper> = (props: ISwiper) => {
  const {
    children = [],
    className,
    autoplay = false,
    autoplayInterval = 3000,
    transitionDuration = 300,
  } = props;

  const currentIndex = useRef<number>(0);
  const swiperRef = useRef<HTMLDivElement>(null);
  const swiperContainerRef = useRef<HTMLDivElement>(null);

  const classNameStr = useMemo(() => {
    return mergeClassName('swiper-widget', className);
  }, [className]);

  useEffect(() => {
    const swiperWidth: number = swiperRef.current!.getBoundingClientRect().width;
    const indicatorList = swiperRef.current!.querySelectorAll<HTMLDivElement>(
      '.swiper-widget__indicator-item'
    );
    if (autoplay) {
      indicatorList[currentIndex.current].classList.add('swiper-widget__indicator-item-active');
      const timer = setInterval(() => {
        if (currentIndex.current === children.length - 1) {
          currentIndex.current = 0;
        } else {
          currentIndex.current++;
        }
        Array.from(indicatorList).forEach((_, index) => {
          indicatorList[index].classList.remove('swiper-widget__indicator-item-active');
        });
        indicatorList[currentIndex.current].classList.add('swiper-widget__indicator-item-active');
        const translateDistance = swiperWidth * currentIndex.current;
        setStyle(swiperContainerRef.current!, {
          transform: `translateX(-${translateDistance}px)`,
          'transition-duration': `${transitionDuration}ms`,
        });
      }, autoplayInterval);
      return () => {
        clearInterval(timer);
      };
    }
  }, [autoplay, autoplayInterval, children.length, transitionDuration]);

  const style = {
    '--auto-interval': `${autoplayInterval}ms`,
  } as CSSProperties;
  return (
    <div style={style} ref={swiperRef} className={classNameStr}>
      <div ref={swiperContainerRef} className="swiper-widget__container">
        {children}
      </div>
      <div className="swiper-widget__indicator">
        {children.map((item, index) => {
          return <div key={index} className="swiper-widget__indicator-item"></div>;
        })}
      </div>
    </div>
  );
};

export default Swiper;
