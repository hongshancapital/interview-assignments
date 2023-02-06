import React, {
  forwardRef,
  PropsWithChildren,
  useCallback,
  useRef,
  useState
} from 'react';
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
  inner: any;
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

    const [sliderWidth, setSliderWidth] = useState(0);

    const { activeIndex, trackStyle, slideTo, startPlay, pausePlay } =
      useCarousel({
        autoplay,
        duration,
        speed,
        slidersCount,
        sliderWidth,
        beforeChange,
        afterChange
      });

    const sliderRef = useRef<HTMLDivElement>();

    const inner = useRef({
      onWindowResized: () => {
        if (sliderRef.current) {
          setSliderWidth(sliderRef.current.clientWidth);
        }
      }
    });

    /**
     * This is done to facilitate jest testing.
     * Because jest.spyOn can only tracks calls to object[methodName], so we need
     * a wrapper and execute target function in it.
     */
    const handleWindowResized = () => inner.current.onWindowResized();

    const containerRefHandler = useCallback(
      (node: HTMLDivElement | null) => {
        if (node) {
          node.style.setProperty('--indicator-anim-duration', duration + 'ms');
          setSliderWidth(node.scrollWidth);
          sliderRef.current = node;

          /**
           * When the screen size changes, adjust the value of translate in CSS to make
           * the slider adapt to the new screen size.
           */
          window.addEventListener('resize', handleWindowResized);
        } else {
          window.removeEventListener('resize', handleWindowResized);
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
        start: startPlay,
        inner: inner.current
      }),
      [activeIndex, inner, pausePlay, slideTo, startPlay]
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
