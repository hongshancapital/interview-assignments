import React, { createRef, ReactElement, useEffect, useMemo, useRef, useState } from 'react';
import { useClassName } from '../../hooks';
import { Aspect, AspectRatio } from '../aspect';
import classes from './Carousel.module.css';
import { CarouselContext } from './CarouselContext';
import { CarouselIndicator } from './CarouselIndicator';
import { CarouselItemProps } from './CarouselItem';

export type CarouselProps = {
  ratio?: AspectRatio;
  autoplay?: boolean | number;
  duration?: number;
  className?: string | undefined | null;
  children?: ReactElement<CarouselItemProps> | ReactElement<CarouselItemProps>[];
}

const getRelativeIndex = (index: number, size: number): number => {
  if (size <= 0) return 0;
  if (index >= 0 && index < size) {
    return index;
  }
  const mod = index % size;
  if (mod < 0) {
    return mod + size;
  }
  return Math.abs(mod);
};

export const Carousel: React.FC<CarouselProps> = ({
  ratio = '16:9',
  autoplay = 3000,
  duration = 500,
  className,
  children,
}) => {
  const swiperRef = createRef<HTMLDivElement>();
  const switchingRef = useRef<false | number>(false);
  const autoplayTimerRef = useRef<NodeJS.Timeout | null>(null);

  const timerTimestampRef = useRef<Date>(new Date());
  const pauseLastRef = useRef<number>(0);
  const pauseOnChangeRef = useRef(false);

  const interval = useMemo(() => {
    if (autoplay === true) return 3000;
    else if (autoplay === false) return 0;
    else if (autoplay <= 0) return 0;
    else if (autoplay < 500) return 500; // 确保最少等待时间
    return autoplay;
  }, [autoplay]);

  const items = children == null ? [] : (Array.isArray(children) ? children : [children]);
  const total = items.length;
  const [index, setIndex] = useState(0);
  const [switching, setSwitching] = useState<false | number>(false);
  const [isPause, setPause] = useState<boolean>(false);
  const [pauseIndex, setPauseIndex] = useState(-1);
  const isAutoplay = useMemo(() => interval > 0 && total > 1 && !isPause, [interval, total, isPause]);

  const clearTimer = () => {
    if (autoplayTimerRef.current != null) {
      clearTimeout(autoplayTimerRef.current);
    }
  };

  const startTimer = (nextIndex: number, ms?: number) => {
    if (!isAutoplay) return;
    clearTimer();
    timerTimestampRef.current = new Date();
    autoplayTimerRef.current = setTimeout(() => {
      changeIndex(nextIndex);
    }, ms || interval);
  };

  useEffect(() => {
    if (isAutoplay) {
      startTimer(index + 1);
    }
  }, []);

  useEffect(() => {
    if (isPause) {
      clearTimer();
    } else {
      startTimer(index + 1, pauseLastRef.current);
    }
  }, [isPause]);

  const onTrackTransitionEnd = () => {
    if (switchingRef.current !== false) {
      if (!pauseOnChangeRef.current) {
        startTimer(switchingRef.current + 1);
      }

      switchingRef.current = false;
      setSwitching(switchingRef.current);
    }
  };

  useEffect(() => {
    if (swiperRef.current != null) {
      swiperRef.current.addEventListener('transitionend', onTrackTransitionEnd);
    }
  }, [swiperRef.current]);

  const changeIndex = (idx: number) => {
    clearTimer();
    const nextIndex = getRelativeIndex(idx, total)
    switchingRef.current = nextIndex;
    setSwitching(switchingRef.current);
    setIndex(nextIndex);
    if (isPause) {
      pauseOnChangeRef.current = true;
      pauseLastRef.current = interval;
      setPauseIndex(nextIndex);
    }
  };

  const swiperStyle = {
    transform : `translateX(-${index * 100}%)`,
    transition: `transform ${duration}ms`,
  };

  const pause = () => {
    if (isAutoplay) {
      const date = new Date();
      let last = interval - (date.valueOf() - timerTimestampRef.current.valueOf());
      if (last <= 0) {
        last = 0;
      }
      pauseLastRef.current = last;
      setPause(true);
      setPauseIndex(index);
    }
  };

  const resume = () => {
    setPause(false);
    pauseOnChangeRef.current = false;
  };

  return (
    <CarouselContext.Provider
      value={{
        ratio,
        current: index,
        total,
        interval,
        duration,
        changeIndex,
        switching,
        isPause,
        pauseIndex,
        pause,
        resume,
      }}
    >
      <Aspect
        ratio={ratio}
        className={useClassName(classes.container, className)}
        onMouseEnter={() => pause()}
        onMouseLeave={() => resume()}
      >
        <div
          ref={swiperRef}
          className={useClassName(classes.swiper, 'carousel-swiper')}
          style={swiperStyle}
        >
          {items}
        </div>
        <CarouselIndicator total={total}/>
      </Aspect>
    </CarouselContext.Provider>
  );
};
