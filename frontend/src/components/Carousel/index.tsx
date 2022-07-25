import React, { useCallback, useEffect, useRef, useState } from 'react';
import cs from 'classnames';

import './index.scss';

export interface CarouselProps {
  autoplay?: boolean;
  duration?: number;
  style?: React.CSSProperties;
  className?: string;
}

const Carousel: React.FC<CarouselProps> = ({
  autoplay = true,
  duration = 3000,
  children,
  style = {},
  className = ''
}) => {
  const itemLength = React.Children.count(children);
  const containerRef = useRef<HTMLDivElement>(null);
  const timer = useRef<number>();
  const [containerWidth, setConatainerWidth] = useState(0);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [hoverIndex, setHoverIndex] = useState(-1);

  const resizeHandler = () => {
    if (containerRef.current) {
      setConatainerWidth(containerRef.current.clientWidth);
    }
  };

  const setTimer = useCallback(() => {
    clearInterval(timer.current);
    timer.current = window.setInterval(() => {
      setCurrentIndex(index => (index === itemLength - 1 ? 0 : index + 1));
    }, duration);
  }, [duration, itemLength]);

  useEffect(() => {
    window.addEventListener('resize', resizeHandler);
    if (containerRef.current) {
      setConatainerWidth(containerRef.current.offsetWidth);
    }
    return () => window.removeEventListener('resize', resizeHandler);
  }, []);

  const handleDotClick = (index: number) => {
    setCurrentIndex(index);
    autoplay && setTimer();
  };

  const handleMouseEnter = (index: number) => {
    clearInterval(timer.current);
    setHoverIndex(index);
  };

  const handleMouseLeave = () => {
    setHoverIndex(-1);
    setTimer();
  };

  useEffect(() => {
    if (autoplay) {
      setTimer();
    }
    return () => clearInterval(timer.current);
  }, [autoplay, setTimer]);

  return (
    <div
      className={cs('carousel-container', className)}
      ref={containerRef}
      style={{ height: '100vh', ...style }}
    >
      <div className='carousel-list'>
        <div
          className='carousel-track'
          style={{
            width: itemLength * containerWidth,
            transform: `translateX(${-currentIndex * containerWidth}px)`
          }}
        >
          {React.Children.map(children, (child, index) => {
            return React.cloneElement(child as React.ReactElement, {
              onClick: () => handleDotClick(index),
              onMouseEnter: () => handleMouseEnter(index),
              onMouseLeave: handleMouseLeave,
              style: {
                width: containerWidth,
                ...((child as React.ReactElement).props.style || {})
              }
            });
          })}
        </div>
      </div>
      <div className='carousel-indicator'>
        {Array.from({ length: itemLength }).map((_, index) => (
          <div
            className='indicator'
            key={index}
            onClick={() => handleDotClick(index)}
          >
            <span
              className={cs(
                'progress',
                autoplay &&
                  currentIndex === index &&
                  hoverIndex !== index &&
                  'animate',
                currentIndex === index ? 'active' : ''
              )}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
