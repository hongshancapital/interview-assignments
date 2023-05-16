import { useState, useEffect, useRef } from 'react';
import { Pagination } from './Pagination/pagination';
import { useAutoPlay, useResizeObserver } from './hooks';
import styles from './carousel.module.scss';

export interface CarouselProps {
  duration?: number;
  speed?: number;
  children: React.ReactNode;
  style?: React.CSSProperties;
  className?: string;
}

export const Carousel = (props: CarouselProps) => {
  const {
    children,
    duration = 1000,
    speed = 250,
    style,
    className,
  } = props;
  const [current, setCurrent] = useState(0);

  const containerRef = useRef<HTMLDivElement>(null);
  const sliderRef = useRef<HTMLDivElement>(null);
  const elements = Array.isArray(children) ? children : [children];
  const total = elements.length;

  const { start: startPlay, stop: stopPlay } = useAutoPlay(
    () => {
      const next = (current + 1) % total;
      setCurrent(next);
    },
    duration
  );

  // Auto play when component is mounted and stop play when it's unmounted
  useEffect(() => {
    startPlay();
    return () => stopPlay();
  }, [startPlay, stopPlay]);

  // Change the x transform offset when the container size change
  useResizeObserver({
    ref: containerRef,
    debounceDelay: 50,
    onResize: () => {
      requestAnimationFrame(() => {
        if (sliderRef.current) {
          const currentWidth = containerRef.current?.getBoundingClientRect().width || 0;
          sliderRef.current.style.transform = `translate3d(-${currentWidth * current}px, 0, 0)`;
        }
      });
    }
  });

  // Dynamically calculate the translateX offset based on the width of container
  const containerWidth = containerRef.current?.getBoundingClientRect().width || 0;
  const sliderStyle = {
    transition: `transform ${speed}ms linear`,
    transform: `translate3d(-${containerWidth * current}px, 0, 0)`
  };

  return (
    <div className={`${styles.container} ${className}`} style={style} ref={containerRef}>
      <div className={styles.slider} style={sliderStyle} ref={sliderRef}>
        {
          elements.map((it, index) => {
            const cls = `${styles.sliderItem} ${current === index ? 'carousel-slider-item-active' : ''}`;
            return <div className={cls} key={index}>{it}</div>
          })
        }
      </div>
      <Pagination
        className={styles.pagination}
        current={current}
        total={total}
        duration={duration}
        onChange={(next) => {
          stopPlay();
          setCurrent(next);
          startPlay();
        }}
      />
    </div>
  )
};