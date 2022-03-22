import { useEffect, useState, useCallback, useMemo } from 'react';
import type { ReactElement } from 'react';
import type { Frame } from '../../type';
import styles from './index.module.css';
import { createInterval } from './createInterval';

interface CarouselItemProps {
  frame: Frame;
  speed?: number;
  currentIndex: number;
  index: number;
  width: string;
}

interface CarouselProps {
  frames: Frame[];
  speed?: number;
  duration?: number;
}

interface PaginationProps {
  active: boolean;
  transitionDuration: string;
}

export function CarouselItem({
  frame,
  index,
  width,
}: CarouselItemProps): ReactElement {
  const { title, description = '', post, backgroundColor, fontColor } = frame;
  return (
    <div
      className={styles['carousel-item']}
      style={{
        backgroundColor,
        transform: `translateX(${index * 100}%)`,
        color: fontColor,
        width,
      }}
    >
      <div className={styles['item-text']}>
        <div className={styles['item-title']}>{title}</div>
        <br />
        <div className={styles['item-description']}>{description}</div>
      </div>
      <img className={styles['item-bg']} src={post} />
    </div>
  );
}

export function Pagination({
  active,
  transitionDuration,
}: PaginationProps): ReactElement {
  return (
    <div className={styles['pagination-item']}>
      {active && (
        <div
          className={styles['pagination-item-active']}
          style={{
            animationDuration: transitionDuration,
          }}
        ></div>
      )}
    </div>
  );
}

export function Carousel({
  frames,
  speed = 2000,
  duration = 5000,
}: CarouselProps): ReactElement {
  const [curIndex, setCurIndex] = useState(0);
  const size: number = frames.length;

  const interval = useMemo(createInterval, []);
  const transitionDuration = `${speed}ms`;

  const play = useCallback(() => {
    interval.setInterval(() => {
      setCurIndex((index) => (index + 1) % size);
    }, duration);
  }, [interval, size, duration]);

  const wrapperStyle = useMemo(() => {
    return {
      transitionDuration,
      transform: `translateX(-${(curIndex * 100) / size}%)`,
      width: `${size * 100}%`,
    };
  }, [curIndex, transitionDuration, size]);

  useEffect(() => {
    play();
    return () => {
      interval.intervalTimer && interval.clearInterval();
    };
  }, [interval, play]);

  return (
    <div className={styles['carousel-container']}>
      <div
        className={styles['carousel-wrapper']}
        style={wrapperStyle}
      >
        {frames.map((frame, index) => (
          <CarouselItem
            key={frame.title}
            frame={frame}
            index={index}
            currentIndex={curIndex}
            width={`${100 / size}%`}
          />
        ))}
      </div>
      <div className={styles['pagination-container']}>
        {frames.map((frame, index) => (
          <Pagination
            key={frame.title}
            active={index === curIndex}
            transitionDuration={transitionDuration}
          />
        ))}
      </div>
    </div>
  );
}
