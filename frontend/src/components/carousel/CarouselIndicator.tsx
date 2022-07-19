import React, { useEffect, useMemo, useRef, useState } from 'react';
import { useClassName } from '../../hooks';
import { useCarouselContext } from './CarouselContext';
import classes from './CarouselIndicator.module.css';

export type CarouselIndicatorProps = {
  total?: number
}

export const CarouselIndicator: React.FC<CarouselIndicatorProps> = ({ total = 0 }) => {
  return (
    <div className={useClassName(classes.indicator, 'carousel-indicator')}>
      {new Array(total).fill(null).map((_, i) => {
        return <CarouselIndicatorBar key={i} index={i}/>;
      })}
    </div>
  );
};

type CarouselIndicatorBarProps = {
  index: number,
};

const CarouselIndicatorBar: React.FC<CarouselIndicatorBarProps> = ({ index }) => {
  const tickerRef = useRef<NodeJS.Timer | null>(null);
  const tickCountRef = useRef<number>(0);

  const tickStep = 1000 / 60;

  const [tickCount, setTickCount] = useState(0);
  const { current, interval, duration, total, isPause, pauseIndex, changeIndex } = useCarouselContext();
  const active = useMemo(() => current === index, [current, index]);

  const clearTicker = () => {
    if (tickerRef.current != null) {
      clearInterval(tickerRef.current);
    }
  };

  const startTicker = (isContinue = false) => {
    clearTicker();
    if (active && interval > 0 && total > 1) {
      if (!isContinue) {
        tickCountRef.current = 0;
        setTickCount(tickCountRef.current);
      }
      tickerRef.current = setInterval(() => {
        tickCountRef.current += tickStep;
        setTickCount(tickCountRef.current);
      }, tickStep);
    }
  };

  useEffect(() => {
    if (current === index) {
      if (isPause) {
        clearTicker();
      } else {
        startTicker(pauseIndex === index);
      }
    } else {
      clearTicker();
      tickCountRef.current = 0;
      setTickCount(0);
    }
  }, [current, index, pauseIndex, isPause]);

  const width = active ? `${(1 - tickCount / (interval + duration)) * 100}%` : '100%';

  return (
    <div className={useClassName(classes.indicatorBar, 'carousel-indicator-bar')}>
      <div
        className={useClassName(classes.indicatorBarProgress, 'carousel-indicator-bar-progress')}
        style={{ width }}
        onClick={() => !active && changeIndex(index)}
      />
    </div>
  );
};
