import { FC, ReactNode, Children, useRef, useState, useEffect, useMemo, useCallback } from 'react';

import './index.css';

interface CarouselProps {
  children?: ReactNode;
  autoplay?: boolean;
  autoplayInterval?: number;
}

const Carousel: FC<CarouselProps> = ({
  children,
  autoplay=true,
  autoplayInterval=3000
}) => {
  const [updateState, setUpdateState] = useState<number>(0);
  const [currentIndex, setCurrentIndex] = useState<number>(-1);
  const timerRef = useRef<number>();
  
  const [carouselContainerWidth, setCarouselContainerWidth] = useState<number>(0);

  const itemCount = useMemo(() => {
    return Children.count(children);
  }, [children]);

  // 当触发点击事件时更新该state，用来清除掉timer从新计时
  const update = useCallback(() => {
    setUpdateState(old => old+1);
  }, []);

  useEffect(() => {
    setCurrentIndex(0);
    update();
  }, [children, update])

  useEffect(() => {
    const _run = () => {
      clearTimeout(timerRef.current);
      if (autoplay) {
        timerRef.current = window.setTimeout(() => {
          setCurrentIndex((oldIndex) => {
            return (oldIndex+1) % itemCount;
          });
          if (autoplay && timerRef.current !== undefined && itemCount > 1) {
            _run();
          }
        }, autoplayInterval)
      }
    }

    if (autoplay && itemCount > 1) {
      _run();
    }

    return () => {
      clearTimeout(timerRef.current);
      timerRef.current = undefined;
    }
  }, [autoplayInterval, autoplay, itemCount, updateState]);

  const dotClick = useCallback((index: number) => {
    setCurrentIndex((oldIndex) => {
      if (index !== oldIndex) {
        update();
      }
      return index;
    })
  }, [update])

  return (
    <div ref={(el) => {
      // 获取轮播图组件宽度
      el && setCarouselContainerWidth(el.offsetWidth);
    }} className='carousel-container'>
      <div className='carousel-list' style={{width: carouselContainerWidth * itemCount, transform: `translateX(${-1 * currentIndex * carouselContainerWidth}px)`}}>
        {
          Children.map(children, (child, index) => (
            <div key={index} className='carousel-item' style={{width: carouselContainerWidth}}>{child}</div>
          ))
        }
      </div>
      <div className='dot-container'>
        {
          new Array(itemCount).fill(undefined).map((_, index) => (
            <div 
              onClick={() => dotClick(index)} 
              key={index} 
              className={`dot ${index === currentIndex ? 'dot-active': ''}`}
            >
              <div style={{transitionDuration: (index === currentIndex && autoplay) ? `${autoplayInterval}ms` : undefined}}/>
            </div>
          ))
        }
      </div>
    </div>
  )
}

export default Carousel;
