import React, { forwardRef, useState, useRef, useEffect, useImperativeHandle } from 'react';
import CarouselDots from './CarouselDots';
import useAnimation from './useAnimation';
import './index.css';

export interface CarouselProps {
  className?: string;
  style?: React.CSSProperties;
  children?: React.ReactNode;
  duration?: number; // 动画时长
}

export interface CarouselRef {
  activeIndex: number;
  goTo: (index: number) => void;
  next: () => void;
  prev: () => void;
}

export default forwardRef<CarouselRef, CarouselProps>(function Carousel({
  className,
  style,
  children,
  duration = 3000,
}, ref) {
  const containerRef = useRef<HTMLDivElement>(null);
  const [slideWidth, setSlideWidth] = useState(0);
  const {
    activeIndex,
    goTo,
    next,
    prev,
  } = useAnimation(Array.isArray(children) ? Array.from(children).length : 1, duration);

  useImperativeHandle(ref, () => ({
    activeIndex,
    goTo,
    next,
    prev,
  }));

  useEffect(() => {
    if (containerRef.current?.clientWidth) {
      setSlideWidth(containerRef.current?.clientWidth);
    }
  }, []);

  if (!Array.isArray(children)) {
    console.error('The children Carousel should be array.');
    return null;
  }

  const trackWidth = slideWidth * Array.from(children).length;
  const trackOffsetX = activeIndex * slideWidth * -1;

  return (
    <div
      className={`carousel ${className}`}
      style={style}
      ref={containerRef}
    >
      <div
        className='carousel__track'
        style={{
          width: trackWidth,
          transform: `translate3d(${trackOffsetX}px, 0px, 0px)`,
          transition: `-webkit-transform 500ms`,
        }}>
        {Array.from(children).map((item, index) => <div key={index} style={{ width: slideWidth }}>{item}</div>)}
      </div>
      <CarouselDots
        className='carousel__dots'
        childrenLength={Array.from(children).length}
        duration={duration}
        activeIndex={activeIndex}
        onDotClick={goTo}
      />
    </div>
  );
});