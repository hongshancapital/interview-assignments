import React, { forwardRef, PropsWithChildren, useCallback } from 'react';
import { useCarousel } from './hooks/useCarousel';
import styles from './styles/Carousel.module.scss';
import { classnames, createIncArray } from '../../utils';

export interface CarouselProps {
  autoplay?: boolean;
  duration?: number;
  speed?: number;
  beforeChange?: (from: number, to: number) => void;
  afterChange?: (current: number) => void;
}

export interface CarouselRef {
  activeIndex: number;
  goTo: (index: number) => void;
  goNext: () => void;
  goPrev: () => void;
  stop: () => void;
  start: () => void;
}

const Carousel = forwardRef<CarouselRef, PropsWithChildren<CarouselProps>>(
  (
    {
      autoplay = true,
      duration = 3000,
      speed = 1000,
      children,
      beforeChange,
      afterChange
    },
    ref
  ) => {
    const slidersCount = React.Children.count(children);

    // Get all indexes of child nodes by its length
    const slidersIndexes = createIncArray(slidersCount);

    const { activeIndex, trackStyle, slideTo, startPlay, pausePlay } =
      useCarousel({
        autoplay,
        duration,
        speed,
        slidersCount,
        beforeChange,
        afterChange
      });

    const containerRefHandler = useCallback(
      (node: HTMLDivElement | null) => {
        if (node) {
          node.style.setProperty('--indicator-anim-duration', duration + 'ms');
        }
      },
      [duration]
    );

    React.useImperativeHandle(
      ref,
      () => ({
        activeIndex,
        goTo: slideTo,
        goNext: () => slideTo(activeIndex + 1),
        goPrev: () => slideTo(activeIndex - 1),
        stop: pausePlay,
        start: startPlay
      }),
      [activeIndex, pausePlay, slideTo, startPlay]
    );

    return (
      <div className={styles.container} ref={containerRefHandler}>
        <div className={styles.track} style={trackStyle}>
          {React.Children.map(children, (child, childIndex) => (
            <div
              className={classnames(styles.slider, {
                [styles['slider-active']]: childIndex === activeIndex
              })}
            >
              {child}
            </div>
          ))}
        </div>
        <ul className={styles.indicators}>
          {slidersIndexes.map(currentIndex => (
            <li key={currentIndex} onClick={() => slideTo(currentIndex)}>
              <div
                className={classnames(styles.indicator, {
                  [styles[`is-active${autoplay ? '-anim' : ''}`]]:
                    currentIndex === activeIndex
                })}
              ></div>
            </li>
          ))}
        </ul>
      </div>
    );
  }
);

export default Carousel;
