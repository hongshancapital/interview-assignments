import React, { useEffect, useState, useRef, useCallback } from 'react';
import Slide from './slide';
import { getSliderListStyles } from './slider-list';
import { CarouselProps, InternalCarouselProps, SlideHeight } from './types';
import renderControls from './controls';
import defaultProps from './default-carousel-props';
import { getIndexes, getNextMoveIndex, getPrevMoveIndex } from './utils';
import useMount from '../../hooks/useMount';
import { Pagination } from './pagination';

export const Carousel = (rawProps: CarouselProps): React.ReactElement => {
  const props = rawProps as InternalCarouselProps;

  const {
    adaptiveHeight,
    afterSlide,
    animation,
    autoplay,
    autoplayInterval,
    autoplayReverse,
    loop,
    beforeSlide,
    cellAlign,
    cellSpacing,
    children,
    className,
    disableAnimation,
    frameAriaLabel,
    innerRef,
    slideIndex,
    slidesToScroll: propsSlidesToScroll,
    slidesToShow,
    speed: propsSpeed,
    style,
    zoomScale,
  } = props;

  const count = React.Children.count(children);

  const [currentSlide, setCurrentSlide] = useState<number>(
    autoplayReverse ? count - slidesToShow : slideIndex
  );
  const [animationEnabled, setAnimationEnabled] = useState<boolean>(false);
  const [move] = useState<number>(0);
  const [frameHeight, setFrameHeight] = useState<number>(0);

  const visibleHeights = useRef<SlideHeight[]>([]);
  const focus = useRef<boolean>(false);
  const carouselEl = useRef<HTMLDivElement>(null);
  const timer = useRef<ReturnType<typeof setTimeout> | null>(null);
  const isMounted = useRef<boolean>(true);

  const slidesToScroll =
    animation === 'fade' ? slidesToShow : propsSlidesToScroll;

  const [slide] = getIndexes(
    currentSlide,
    currentSlide - slidesToScroll,
    count
  );

  useEffect(() => {
    isMounted.current = true;
    return () => {
      isMounted.current = false;
    };
  }, []);

  useMount(() => {
    // disable img draggable attr by default
    document
      .querySelectorAll('.slider-list img')
      .forEach((el) => el.setAttribute('draggable', 'false'));
  });

  const getNextIndex = useCallback(
    (to?: number) => {
      const index = to ?? currentSlide;
      if (index < 0) {
        return index + count;
      }
      if (index === count) {
        return 0;
      }
      return index;
    },
    [count, currentSlide]
  );

  const moveSlide = useCallback(
    (to?: number) => {
      const nextIndex = getNextIndex(to);
      typeof to === 'number' && beforeSlide(slide, nextIndex);

      !disableAnimation && setAnimationEnabled(true);

      if (typeof to === 'number') {
        setCurrentSlide(to);
      }

      setTimeout(
        () => {
          if (!isMounted.current) return;
          typeof to === 'number' && afterSlide(nextIndex);
          !disableAnimation && setAnimationEnabled(false);
        },
        !disableAnimation ? propsSpeed || 500 : 40
      ); // if animation is disabled decrease the speed to 40
    },
    [slide, afterSlide, beforeSlide, disableAnimation, getNextIndex, propsSpeed]
  );

  const nextSlide = useCallback(() => {
    if (currentSlide < count - propsSlidesToScroll) {
      const nextPosition = getNextMoveIndex(
        currentSlide,
        count,
        propsSlidesToScroll,
        slidesToShow
      );
      moveSlide(nextPosition);
    }
  }, [count, currentSlide, moveSlide, propsSlidesToScroll, slidesToShow]);

  const prevSlide = useCallback(() => {
    // boundary
    if (currentSlide > 0) {
      const prevPosition = getPrevMoveIndex(currentSlide, propsSlidesToScroll);

      moveSlide(prevPosition);
    }
  }, [currentSlide, moveSlide, propsSlidesToScroll]);

  // When user changed the slideIndex property from outside.
  useEffect(() => {
    if (typeof slideIndex === 'number' && !autoplayReverse) {
      moveSlide(slideIndex);
    }
  }, [slideIndex, autoplayReverse]); // eslint-disable-line react-hooks/exhaustive-deps

  // Makes the carousel infinity when autoplay are enabled
  useEffect(() => {
    if (autoplay && !animationEnabled) {
      if (currentSlide > count) {
        setCurrentSlide(currentSlide - count);
        if (timer?.current) {
          clearTimeout(timer.current);
        }
      } else if (currentSlide < 0) {
        setCurrentSlide(count - -currentSlide);
        if (timer?.current) {
          clearTimeout(timer.current);
        }
      }
    }
  }, [animationEnabled, currentSlide, count, autoplay]);

  useEffect(() => {
    if (autoplay) {
      timer.current = setTimeout(() => {
        if (autoplayReverse) {
          if (currentSlide > 0) {
            prevSlide();
          } else {
            prevSlide();
          }
        } else if (currentSlide < count - slidesToShow) {
          nextSlide();
        } else if (loop && currentSlide >= count - slidesToShow) {
          setCurrentSlide(0);
        } else {
          nextSlide();
        }
      }, autoplayInterval);
    }

    // Clear the timeout if user hover on carousel
    if (autoplay) {
      if (!loop) {
        if (timer?.current && currentSlide >= count - slidesToShow) {
          clearTimeout(timer.current);
        }
      }

      if (autoplayReverse && timer?.current && currentSlide <= 0) {
        clearTimeout(timer.current);
      }
    }

    return () => {
      if (timer.current) {
        clearTimeout(timer.current);
      }
    };
  }, [
    currentSlide,
    slidesToShow,
    count,
    autoplay,
    autoplayInterval,
    autoplayReverse,
    prevSlide,
    nextSlide,
    loop,
  ]);

  // 启用 wrapAround 时使轮播无限，但禁用自动播放
  useEffect(() => {
    let prevTimeout: ReturnType<typeof setTimeout> | null = null;
    let nextTimeout: ReturnType<typeof setTimeout> | null = null;

    if (!autoplay) {
      // if animation is disabled decrease the speed to 0
      const speed = !disableAnimation ? propsSpeed || 500 : 0;

      if (currentSlide <= -slidesToShow) {
        // prev
        prevTimeout = setTimeout(() => {
          if (!isMounted.current) return;
          setCurrentSlide(count - -currentSlide);
        }, speed + 10);
      } else if (currentSlide >= count) {
        // next
        nextTimeout = setTimeout(() => {
          if (!isMounted.current) return;
          setCurrentSlide(currentSlide - count);
        }, speed + 10);
      }
    }

    return function cleanup() {
      if (prevTimeout) {
        clearTimeout(prevTimeout);
      }
      if (nextTimeout) {
        clearTimeout(nextTimeout);
      }
    };
  }, [
    currentSlide,
    autoplay,
    disableAnimation,
    propsSpeed,
    slidesToShow,
    count,
  ]);

  const renderSlides = (typeOfSlide?: 'prev-cloned' | 'next-cloned') => {
    const slides = React.Children.map(children, (child, index) => {
      const isCurrentSlide = currentSlide === index;

      return (
        <Slide
          key={`${typeOfSlide}-${index}`}
          count={count}
          currentSlide={currentSlide}
          index={index}
          isCurrentSlide={isCurrentSlide}
          typeOfSlide={typeOfSlide}
          cellSpacing={cellSpacing}
          animation={animation}
          slidesToShow={slidesToShow}
          speed={propsSpeed}
          zoomScale={zoomScale}
          cellAlign={cellAlign}
          setFrameHeight={setFrameHeight}
          frameHeight={frameHeight}
          visibleHeights={visibleHeights}
          adaptiveHeight={adaptiveHeight}
        >
          {child}
        </Slide>
      );
    });

    return slides;
  };

  return (
    <div
      className={'slider-container'}
      style={{
        position: 'relative',
      }}
    >
      {renderControls(
        props,
        count,
        currentSlide,
        moveSlide,
        nextSlide,
        prevSlide,
        slidesToScroll
      )}

      <div
        className={['slider-frame', className || ''].join(' ').trim()}
        style={{
          overflow: 'hidden',
          width: '100%',
          position: 'relative',
          outline: 'none',
          height: adaptiveHeight ? `${frameHeight}px` : 'auto',
          ...style,
        }}
        aria-label={frameAriaLabel}
        role="region"
        tabIndex={0}
        onFocus={() => (focus.current = true)}
        onBlur={() => (focus.current = false)}
        ref={innerRef || carouselEl}
      >
        <div
          className="slider-list"
          style={getSliderListStyles(
            children,
            currentSlide,
            animationEnabled,
            slidesToShow,
            cellAlign,
            propsSpeed,
            move,
            animation
          )}
        >
          {renderSlides()}
        </div>
      </div>
      <Pagination
        count={count}
        currentSlide={currentSlide}
        delay={defaultProps.autoplayInterval}
      />
    </div>
  );
};

Carousel.defaultProps = defaultProps;

export default Carousel;
