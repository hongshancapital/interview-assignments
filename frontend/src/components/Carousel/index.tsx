import React, { FC, useRef, useState, useEffect, useCallback, useMemo } from "react";
import CarouselItem, { CarouselItemProps } from '../CarouselItem';
import IndicatorItem from '../IndicatorItem';
import './index.scss';

interface CarouselProps {
  // 轮播图内容项
  items: CarouselItemProps[];
  // 起始播放位置
  startIndex?: number;
  // 自动播放
  autoPlay?: boolean;
  // 停留时间
  duration?: number;
  // 切换速度
  speed?: number;
  // 是否显示滑动指示条
  showIndicators?: boolean;
}

const Carousel: FC<CarouselProps> = ({
  items,
  startIndex = 0,
  autoPlay = true,
  duration = 3000,
  speed = 500,
  showIndicators = true,
}) => {
  const len = items.length;
  const initIndex = startIndex >= 0 && startIndex < len ? startIndex : 0;
  const [activeIndex, setActiveIndex] = useState(initIndex);
  const timerRef: any = useRef(0);  

  const playAnimation = useCallback(() => {
    if (duration > 0) {
      timerRef.current = setInterval(() => {
        setActiveIndex((prev) => (prev + 1) % len);
      }, duration);
    }
  }, [duration, len]);

  const clearAnimation = useCallback(() => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }
  }, []);

  useEffect(() => {
    if (autoPlay) {
      playAnimation();
    }
    return () => {
      clearAnimation();
    };
  }, [autoPlay, playAnimation, clearAnimation]);

  const handleIndicatorClick = useCallback((index: number) => {
    setActiveIndex(index);
    if (autoPlay) {
      clearAnimation();
      playAnimation();
    }
  }, [autoPlay, playAnimation, clearAnimation]);

  const transformStyle = useMemo(() => ({
    width: `${len * 100}%`,
    transform: `translateX(-${activeIndex / len * 100}%)`,
    transition: `all ${speed}ms`,
  }), [len, activeIndex, speed]);

  return (
    <div className="carousel-wrapper">
      <div className="carousel-list" style={transformStyle}>
        {
          items.map((item: CarouselItemProps, index: number) => <CarouselItem key={index} {...item} />)
        }
      </div>
      {
        showIndicators && (
          <div className="carousel-indicators">
            {
              items.map((item: CarouselItemProps, index: number) => (
                <IndicatorItem
                  key={index}
                  autoPlay={autoPlay}
                  active={index === activeIndex}
                  duration={duration}
                  onClick={() => handleIndicatorClick(index)}
                />
              ))
            }
          </div>
        )
      }
    </div>
  );
};

export default Carousel;