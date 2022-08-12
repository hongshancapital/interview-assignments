import React, { useState, useRef, useEffect, CSSProperties, useCallback } from 'react';
import cls from 'classnames';

import Indicator from './Indicator';
import { isFunc } from '../../util';

import './index.scss';

interface CarouselProps {
  className?: string;
  style?: CSSProperties;
  autoplay?: boolean;
  animate?: boolean;
  duration?: number;
  beforeChange?: (current: number) => void;
  afterChange?: (from: number, to: number) => void;
}

const DEFAULT_DURATION = 3000;

const Carousel: React.FC<CarouselProps> = ({
  children,
  beforeChange,
  afterChange,
  autoplay = true,
  animate = true,
  duration = DEFAULT_DURATION,
  ...props
}) => {
  const [current, setCurrent] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [slideWidth, setSlideWidth] = useState(0);
  const currentRef = useRef(current);
  const intervalStartTimeRef = useRef(Date.now());
  const remainTimeRef = useRef(duration);
  const carouselRef = useRef<HTMLDivElement>(null);
  const intervalRef = useRef<number>();
  const timeoutRef = useRef<number>();
  const prevIndexRef = useRef(current);
  const _mountedRef = useRef(false);

  const goTo = useCallback((idx: number, reset: boolean = false) => {
    const newCurrent = currentRef.current;

    if (isFunc(beforeChange) && !reset) {
      beforeChange(newCurrent);
    }

    if (isFunc(afterChange)) {
      prevIndexRef.current = newCurrent;
    }
    setCurrent(idx);
    currentRef.current = idx;
  }, [beforeChange, afterChange]);

  const arrChildren = React.Children.toArray(children).filter(child => !!child);
  const arrChildrenLen = arrChildren.length;

  const updateIntervalStartTime = () => {
    intervalStartTimeRef.current = Date.now();
  };

  const next = useCallback(() => {
    updateIntervalStartTime();
    if (animate) {
      carouselRef.current?.classList.add('animate');
    }
    const newCurrent = currentRef.current;

    goTo(!animate && (newCurrent >= arrChildrenLen - 1) ? 0 : newCurrent + 1);
  }, [animate, arrChildrenLen, goTo]);

  const reset = () => goTo(0, true);

  const start = useCallback(() => {
    stop();
    setIsPlaying(true);
    intervalRef.current = window.setInterval(next, duration);
  }, [duration, next]);

  const pause = useCallback(() => {
    if (!autoplay) {
      return;
    }

    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }

    if (intervalRef.current) {
      clearInterval(intervalRef.current);
    }
    setIsPlaying(false);
    remainTimeRef.current -= (Date.now() - intervalStartTimeRef.current);
  }, [autoplay]);

  const resume = useCallback(() => {
    if (!autoplay || isPlaying) {
      return;
    }
    setIsPlaying(true);
    updateIntervalStartTime();
    const remainTime = remainTimeRef.current;

    if (remainTime > 0) {
      timeoutRef.current = window.setTimeout(() => {
        next();
        start();
        clearTimeout(timeoutRef.current);
      }, remainTime);
    }
  }, [autoplay, next, start, isPlaying]);

  const stop = () => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }

    if (intervalRef.current) {
      clearInterval(intervalRef.current);
    }
    setIsPlaying(false);
  }

  useEffect(() => {
    if (autoplay) {
      remainTimeRef.current = duration;
    }
  }, [autoplay, current, duration]);

  useEffect(() => {
    if (isFunc(afterChange) && !animate && _mountedRef.current) {
      afterChange(prevIndexRef.current, current);
    }
  }, [current, animate, afterChange]);

  useEffect(() => {
    _mountedRef.current = true;

    if (carouselRef.current) {
      setSlideWidth(carouselRef.current.getBoundingClientRect().width);
    }
    const onVisibilityChange = () => {
      if (document.hidden) {
        pause();
      } else {
        resume()
      }
    };

    document.addEventListener('visibilitychange', onVisibilityChange, false);
    return () => document.removeEventListener('visibilitychange', onVisibilityChange);
  }, [pause, resume]);

  useEffect(() => {
    if (autoplay) {
      start();
    } else {
      stop();
    }
    return stop;
  }, [autoplay, start]);
  
  const computedSlideStyle: CSSProperties = { width: slideWidth };

  const wrappedChildren = arrChildren.map((child, idx) => (
    <div className="slide" key={idx} style={computedSlideStyle}>
      {child}
    </div>
  ));

  if (wrappedChildren.length > 1) {
    const firstChild = arrChildren[0];
    const copyFirstChild = React.isValidElement(firstChild) ? React.cloneElement(firstChild) : firstChild;

    wrappedChildren.push(
      <div className="slide" key={wrappedChildren.length + 1} style={computedSlideStyle}>
        {copyFirstChild}
      </div>
    );
  }
  const className = cls('carousel', props.className);
  const style = props.style || {};
  const computedSlideContainerStyle: CSSProperties = {
    width: slideWidth * wrappedChildren.length,
    transform: `translate3d(-${slideWidth * (current % wrappedChildren.length) }px, 0, 0)`
  };

  const onSlideContainerTransitionEnd = animate
    ? () => {
      carouselRef.current?.classList.remove('animate');
      const needReset = current >= arrChildrenLen;

      if (isFunc(afterChange)) {
        afterChange(prevIndexRef.current, needReset ? 0 : current);
      }
      
      if (needReset) {
        reset();
      }
    }
    : () => {};

  const jump = (idx: number) => {
    goTo(idx);
    stop();
  }

  return (
    <div
      ref={carouselRef}
      className={className}
      style={style}
      onMouseEnter={pause}
      onMouseLeave={resume}
    >
      <div className="slider-wrapper">
        <div
          className="slide-container"
          style={computedSlideContainerStyle}
          onTransitionEnd={onSlideContainerTransitionEnd}
        >
          {wrappedChildren}
        </div>
        <Indicator
          autoplay={autoplay}
          isPlaying={isPlaying}
          duration={duration}
          count={arrChildrenLen}
          current={current}
          goTo={jump}
        />
      </div>
    </div>
  );
};

export default Carousel;
